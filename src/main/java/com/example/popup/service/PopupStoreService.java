package com.example.popup.service;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.dto.ProductDto;
import com.example.popup.entity.PopupStore;
import com.example.popup.entity.Product;
import com.example.popup.repository.PopupStoreRepository;
import com.example.popup.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final ProductRepository productRepository;

    // 팝업스토어 개설과 동시에 초기 상품 등록
    public ResponseEntity<?> createPopupStore(PopupCreationRequest request) {
        PopupStoreDto storeDto = request.getPopupStoreDto();
        ProductDto productDto = request.getProductDto();

        // 날짜 문자열을 LocalDateTime으로 변환
        PopupStore popupStore = PopupStore.builder()
                .email(storeDto.getEmail())
                .user(storeDto.getUser())
                .saleStart(PopupStore.parseDateTime(storeDto.getSaleStart()))
                .saleEnd(PopupStore.parseDateTime(storeDto.getSaleEnd()))
                .build();

        // 팝업스토어 저장 (PopupID 생성)
        popupStore = popupStoreRepository.save(popupStore);

        // 초기 상품 등록
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productDescription(productDto.getProductDescription())
                .price(productDto.getPrice())
                .popupStore(popupStore)
                .build();
        productRepository.save(product);
        popupStore.getProducts().add(product);
        popupStoreRepository.save(popupStore);

        return ResponseEntity.ok("팝업스토어 개설과 상품 등록이 완료되었습니다.");
    }
}
