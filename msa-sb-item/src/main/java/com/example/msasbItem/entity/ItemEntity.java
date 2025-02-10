package com.example.msasbItem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 제품 테이블
 * 더미 데이터 추가
 * insert into products values ('1','1000000','macbook air','100');
 * insert into products values ('2','2000000','macbook pro','100');
 * insert into products values ('3','30000000','imac','100');
 * insert into products values ('4','40000000','imac pro','100');
 */

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

    @Builder
    public ItemEntity(Long popId, String name, Long amount, Long price, String des) {
        this.popId = popId;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.des = des;
    }

    // ownerEmail Getter
    public String getOwnerEmail() {
        return email;
    }
}


