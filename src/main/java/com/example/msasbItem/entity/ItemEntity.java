package com.example.msasbItem.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="item")
@Data
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private Long popId;
    private String name;
    private Long amount;
    private Long price;
    private String des;
    private String email;

    // Json으로 변환
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> imageUrls;

    @Builder
    public ItemEntity(Long popId, String name, Long amount, Long price, String des, String email, List<String> imageUrls) {
        this.popId = popId;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.des = des;
        this.email = email;
        this.imageUrls = imageUrls;
    }
}


