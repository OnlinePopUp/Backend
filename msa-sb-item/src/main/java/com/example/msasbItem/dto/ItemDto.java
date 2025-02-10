package com.example.msasbItem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long itemId;
    private Long popId;
    private String name;  // 아이템 이름
    private Long amount;  // 재고 수량
    private Long price;   // 가격
    private String des;   // 설명

    @Builder
    public ItemDto(Long itemId, Long popId, String name, Long amount, Long price, String des) {
        this.itemId = itemId;
        this.popId = popId;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.des = des;
    }
}
