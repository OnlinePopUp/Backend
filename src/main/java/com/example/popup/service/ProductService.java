package com.example.popup.service;

import com.example.popup.entity.Product;
import com.example.popup.entity.PopupStore;
import com.example.popup.repository.PopupStoreRepository;
import com.example.popup.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final PopupStoreRepository popupStoreRepository;

    // 상품 등록
    public ResponseEntity<?> addProduct(Long popupStoreId, String productName, String productDescription,
                                        BigDecimal price, int inventory, String imageUrl) {
        Optional<PopupStore> storeOpt = popupStoreRepository.findById(popupStoreId);
        if (storeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 팝업 스토어가 존재하지 않습니다.");
        }

        Product product = Product.builder()
                .popupStore(storeOpt.get())
                .productName(productName)
                .productDescription(productDescription)
                .price(price)
                .inventory(inventory)
                .imageUrl(imageUrl)
                .build();
        productRepository.save(product);
        return ResponseEntity.ok("상품 등록이 완료되었습니다.");
    }
}
