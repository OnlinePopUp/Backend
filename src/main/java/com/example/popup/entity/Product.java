package com.example.popup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;  // 상품 고유 식별자

    private String productName;         // 상품 이름
    private String productDescription;  // 상품 설명
    private BigDecimal price;           // 상품 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id")
    private PopupStore popupStore;
}
