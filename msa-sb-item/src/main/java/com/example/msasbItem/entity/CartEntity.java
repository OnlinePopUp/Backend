package com.example.msasbItem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cart")
@Data
@NoArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long cartId;
    private Long itemId;
    private String email;
    private Long popId;
    private Long price;
    private Long amount;
    private String itemName;

    @Builder
    public CartEntity(Long cartId, Long itemId, String email, Long price, Long amount, String itemName, Long popId) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.email = email;
        this.price = price;
        this.amount = amount;
        this.itemName = itemName;
        this.popId = popId;
    }
}
