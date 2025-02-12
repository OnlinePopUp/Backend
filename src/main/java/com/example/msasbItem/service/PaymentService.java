package com.example.msasbItem.service;

import com.example.msasbItem.dto.PaymentDto;
import com.example.msasbItem.entity.PaymentEntity;
import com.example.msasbItem.jwt.JwtUtil;
import com.example.msasbItem.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final JwtUtil jwtUtil;
    
    // 결제 정보 조회
    public List<PaymentDto> getPayment(String token) {
        String email = jwtUtil.getEmail(token);
        List<PaymentEntity> paymentEntities = paymentRepository.findByBuyerEmail(email);

        return paymentEntities.stream()
                .map(p -> PaymentDto.builder()
                        .paymentId(p.getPaymentId())
                        .orderItem(p.getOrderItem())
                        .buyerEmail(p.getBuyerEmail())
                        .orderDate(p.getOrderDate())
                        .totalPrice(p.getTotalPrice())
                        .build()).toList();
    }
}
