package com.example.popup.service;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.entity.PopupStore;
import com.example.popup.repository.PopupStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;

    // 팝업스토어 개설 API (상품 등록 없이 팝업스토어만 생성)
    public ResponseEntity<?> createPopupStore(PopupCreationRequest request) {
        // PopupCreationRequest에 팝업스토어 정보만 포함되어 있다고 가정합니다.
        PopupStoreDto storeDto = request.getPopupStoreDto();

        PopupStore popupStore = PopupStore.builder()
                .email(storeDto.getEmail())
                .title(storeDto.getTitle())
                .content(storeDto.getContent())
                .start(PopupStore.parseDateTime(storeDto.getStart()))
                .exp(PopupStore.parseDateTime(storeDto.getExp()))
                .offline(storeDto.getOffline())
                .address(storeDto.getAddress())
                .category(storeDto.getCategory())
                .image(storeDto.getImage())
                .build();

        popupStoreRepository.save(popupStore);

        return ResponseEntity.ok("팝업스토어 개설이 완료되었습니다.");
    }
}
