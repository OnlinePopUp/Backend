package com.example.popup.dto;

import lombok.Data;

@Data
public class PopupCreationRequest {
    private PopupStoreDto popupStoreDto; // 팝업스토어 개설 정보
    private ProductDto productDto;       // 초기 등록할 상품 정보
}
