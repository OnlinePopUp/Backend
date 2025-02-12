package com.example.msasbItem.repository;

import com.example.msasbItem.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByBuyerEmail(String buyerEmail);
    PaymentEntity findByPaymentId(long paymentId);
}
