package com.example.msasbItem.repository;

import com.example.msasbItem.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByEmailAndItemIdAndPopId(String email, Long itemId, Long popId);
    List<CartEntity> findByEmail(String email);
}
