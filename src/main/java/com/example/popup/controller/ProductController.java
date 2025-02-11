package com.example.popup.controller;

import com.example.popup.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popup-store/{storeId}/product")
public class ProductController {
    private final ProductService productService;

    // 상품 등록 API
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@PathVariable Long storeId,
                                        @RequestParam String productName,
                                        @RequestParam String productDescription,
                                        @RequestParam BigDecimal price,
                                        @RequestParam int inventory,
                                        @RequestParam String imageUrl) {
        return productService.addProduct(storeId, productName, productDescription, price, inventory, imageUrl);
    }
}
