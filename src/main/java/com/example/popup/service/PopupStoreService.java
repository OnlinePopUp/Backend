package com.example.popup.service;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.entity.PopupStore;
import com.example.popup.repository.PopupStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;

    // 팝업스토어 개설 API (상품 등록 없이 팝업스토어만 생성)
    public ResponseEntity<?> createPopupStore(String email, PopupCreationRequest request) {
        // PopupCreationRequest에 팝업스토어 정보만 포함되어 있다고 가정합니다.
        PopupStoreDto storeDto = request.getPopupStoreDto();

        PopupStore popupStore = PopupStore.builder()
                .email(email)
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
    };

    // 팝업스토어 전체 조회 API
    public ResponseEntity<?> getAllPopupStores() {
        List<PopupStore> popups = popupStoreRepository.findAll();
        return ResponseEntity.ok(popups);
    }

    // 자신이 올린 팝업스토어 수정 API
    public ResponseEntity<?> updatePopupStore(String email, Long popId, PopupStoreDto updatedDto) {
        Optional<PopupStore> optionalPopup = popupStoreRepository.findById(popId);
        if (!optionalPopup.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("팝업스토어를 찾을 수 없습니다.");
        }
        PopupStore popupStore = optionalPopup.get();

        // 수정 권한 확인 (팝업스토어 소유자와 요청한 이메일이 일치하는지)
        if (!popupStore.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        // 팝업스토어 정보 업데이트
        popupStore.setTitle(updatedDto.getTitle());
        popupStore.setContent(updatedDto.getContent());
        popupStore.setStart(PopupStore.parseDateTime(updatedDto.getStart()));
        popupStore.setExp(PopupStore.parseDateTime(updatedDto.getExp()));
        popupStore.setOffline(updatedDto.getOffline());
        popupStore.setAddress(updatedDto.getAddress());
        popupStore.setCategory(updatedDto.getCategory());
        popupStore.setImage(updatedDto.getImage());

        popupStoreRepository.save(popupStore);

        return ResponseEntity.ok("팝업스토어 수정이 완료되었습니다.");
    }

}
