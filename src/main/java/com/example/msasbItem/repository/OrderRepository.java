package com.example.msasbItem.repository;

import com.example.msasbItem.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> findByEmailAndPaymentId(String email, Long paymentId);
}
