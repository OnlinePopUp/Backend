package com.example.msasbItem.dto;

import com.example.msasbItem.entity.StringListConverter;
import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long itemId;
    private Long popId;
    private String name;
    private Long amount;
    private Long price;
    private String des;
    private String email;
    private List<String> imageUrls; // 여러 개의 이미지 저장

    @Builder
    public ItemDto(Long itemId, Long popId, String name, Long amount, Long price, String des, String email, List<String> imageUrls) {
        this.itemId = itemId;
        this.popId = popId;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.des = des;
        this.email = email;
        this.imageUrls = imageUrls;
    }
}
