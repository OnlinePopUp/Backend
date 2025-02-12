package com.example.msasbItem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private String orderItem;
    private String buyerEmail;
    private String orderDate;
    private Long totalPrice;

    @Builder
    public PaymentDto(Long paymentId, String orderItem, String buyerEmail, String orderDate, Long totalPrice) {
        this.paymentId = paymentId;
        this.orderItem = orderItem;
        this.buyerEmail = buyerEmail;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
