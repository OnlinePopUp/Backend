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
    private Long id;

    // PopupStore와의 관계: Many-to-One
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_store_id")
    private PopupStore popupStore;

    private String productName;
    private String productDescription;
    private BigDecimal price;
    private int inventory;
    private String imageUrl;
}
