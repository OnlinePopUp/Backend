package com.example.msasbItem.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_table")  // order는 예약어라 order_table로 설정
@Data
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private CartEntity cart;  // 장바구니 연관 관계

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private ItemEntity item;  // 아이템 연관 관계

    @ManyToOne
    @JoinColumn(name = "popId", nullable = false)
    private PopUpEntity popUp;  // 팝업 연관 관계

    @Column(nullable = false)
    private String email;  // 구매자 이메일

    private Long totalPrice;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhone;

    @Builder
    public OrderEntity(CartEntity cart, ItemEntity item, PopUpEntity popUp, String email,
                       Long totalPrice, String buyerName, String buyerAddress, String buyerPhone) {
        this.cart = cart;
        this.item = item;
        this.popUp = popUp;
        this.email = email;
        this.totalPrice = totalPrice;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerPhone = buyerPhone;
    }
}