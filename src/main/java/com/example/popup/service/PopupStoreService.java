package com.example.popup.service;

import com.example.popup.entity.PopupStore;
import com.example.popup.repository.PopupStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopupStoreService {
    private final PopupStoreRepository popupStoreRepository;

    // 팝업 스토어 개설
    public ResponseEntity<?> createStore(String storeName, String description, String ownerEmail,
                                         LocalDateTime saleStart, LocalDateTime saleEnd) {
        PopupStore store = PopupStore.builder()
                .name(storeName)
                .description(description)
                .email(ownerEmail)
                .saleStart(saleStart)
                .saleEnd(saleEnd)
                .build();
        popupStoreRepository.save(store);
        return ResponseEntity.ok("팝업 스토어가 성공적으로 개설되었습니다.");
    }

    // 팝업 스토어 조회, 수정 등 추가 기능 구현 가능
    public ResponseEntity<?> getStore(Long storeId) {
        Optional<PopupStore> storeOpt = popupStoreRepository.findById(storeId);
        if (storeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("존재하지 않는 스토어입니다.");
        }
        return ResponseEntity.ok(storeOpt.get());
    }
}
