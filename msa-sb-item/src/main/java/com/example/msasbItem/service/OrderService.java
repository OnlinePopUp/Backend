package com.example.msasbItem.service;

import com.example.msasbItem.dto.CartDto;
import com.example.msasbItem.dto.ItemDto;
import com.example.msasbItem.dto.OrderDto;
import com.example.msasbItem.entity.CartEntity;
import com.example.msasbItem.entity.ItemEntity;
import com.example.msasbItem.entity.OrderEntity;
import com.example.msasbItem.entity.PopUpEntity;
import com.example.msasbItem.repository.CartRepository;
import com.example.msasbItem.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    // 주문 처리
    public void orderItem(String email, OrderDto orderDto) {
        List<CartEntity> cartItems = cartRepository.findByEmail(email);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있어 주문할 수 없습니다.");
        }

        // 현재 시간 구하기
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        orderDto.setOrderDate(currentDate);

        // 장바구니의 모든 아이템을 주문으로 변환하여 저장
        for (CartEntity cartItem : cartItems) {
            OrderEntity orderEntity = OrderEntity.builder()
                    .itemId(cartItem.getItemId())
                    .popId(cartItem.getPopId())
                    .email(email)
                    .totalPrice(cartItem.getPrice())
                    .buyerName(orderDto.getBuyerName())
                    .buyerAddress(orderDto.getBuyerAddress())
                    .buyerPhone(orderDto.getBuyerPhone())
                    .totalAmount(cartItem.getAmount())
                    .orderDate(orderDto.getOrderDate())
                    .build();

            orderRepository.save(orderEntity);
        }

        // 주문이 완료되었으므로 장바구니 삭제
        cartRepository.deleteByEmail(email);
    }

    // 아이템 조회
    public List<OrderDto> getOrderItems(String email) {
        List<OrderEntity> orderEntities = orderRepository.findByEmail(email);

        return orderEntities.stream()
                .map(i -> OrderDto.builder()
                        .orderId(i.getOrderId())
                        .itemId(i.getItemId())
                        .popId(i.getPopId())
                        .email(i.getEmail())
                        .totalPrice(i.getTotalPrice())
                        .buyerName(i.getBuyerName())
                        .buyerAddress(i.getBuyerAddress())
                        .buyerPhone(i.getBuyerPhone())
                        .totalAmount(i.getTotalAmount())
                        .orderDate(i.getOrderDate())
                        .build())
                .toList();
    }
}
