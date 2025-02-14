package com.example.popup.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String productName;
    private String productDescription;
    private BigDecimal price;
}
