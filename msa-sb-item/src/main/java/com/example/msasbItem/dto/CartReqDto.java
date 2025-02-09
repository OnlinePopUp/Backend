package com.example.msasbItem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartReqDto {

    private Long itemId;
    private String email;
    private Integer totalPrice;
    private Integer quantity;
    private String itemName;
    private Long popId; // popUpEntity의 ID만 전달

    @Builder
    public CartReqDto(Long itemId, String email, Integer totalPrice, Integer quantity, String itemName, Long popId) {
        this.itemId = itemId;
        this.email = email;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.itemName = itemName;
        this.popId = popId;
    }
}