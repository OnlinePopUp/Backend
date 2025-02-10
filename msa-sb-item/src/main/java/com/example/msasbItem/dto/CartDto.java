package com.example.msasbItem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {
    private Long itemId;
    private String email;
    private Long price;
    private Long amount;
    private String itemName;
    private Long popId; // popUpEntity의 ID만 전달

    @Builder
    public CartDto(Long itemId, String email, Long price, Long amount, String itemName, Long popId) {
        this.itemId = itemId;
        this.email = email;
        this.price = price;
        this.amount = amount;
        this.itemName = itemName;
        this.popId = popId;
    }
}