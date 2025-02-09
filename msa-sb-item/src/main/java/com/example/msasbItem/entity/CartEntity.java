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
    @ManyToOne
    @JoinColumn(name = "popId", nullable = false)
    private PopUpEntity popId;
    private Integer totalPrice;
    private Integer quantity;
    private String itemName;

    @Builder
    public CartEntity(Long cartId, Long itemId, String email, Integer totalPrice, Integer quantity, String itemName) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.email = email;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.itemName = itemName;
    }
}
