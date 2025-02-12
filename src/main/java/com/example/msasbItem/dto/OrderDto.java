package com.example.msasbItem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private Long itemId;
    private Long popId;
    private String email;  // 구매자 이메일
    private Long totalPrice;
    private String itemName;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhone;
    private Long totalAmount;
    private String orderDate;
    private String imageUrl;
    private Long paymentId;

    @Builder
    public OrderDto(Long orderId, Long itemId, Long popId, String email,
                    Long totalPrice, String itemName, String buyerName, String buyerAddress,
                    String buyerPhone, Long totalAmount, String orderDate,
                    String imageUrl, Long paymentId) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.popId = popId;
        this.email = email;
        this.totalPrice = totalPrice;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerPhone = buyerPhone;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.imageUrl = imageUrl;
        this.paymentId = paymentId;
    }
}
