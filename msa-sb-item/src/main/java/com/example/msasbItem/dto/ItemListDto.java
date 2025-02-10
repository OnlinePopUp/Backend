package com.example.msasbItem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemListDto {
    private String name;  // 아이템 이름
    private Long price;

    @Builder
    public ItemListDto(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
