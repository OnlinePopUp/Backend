package com.example.popup.controller;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.service.PopupStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/popup")
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    // 팝업스토어 개설과 동시에 상품 등록 API
    @PostMapping("/create")
    public ResponseEntity<?> createPopupStore(
            @RequestHeader("X-Auth-User") String email,
            @RequestBody PopupCreationRequest request) {
        return popupStoreService.createPopupStore(email, request);
    }


    // 팝업스토어 전체 조회 API
    @GetMapping("")
    public ResponseEntity<?> getAllPopupStores() {
        return popupStoreService.getAllPopupStores();
    }

    // 자신이 올린 팝업스토어 수정 API
    @PutMapping("/{popId}")
    public ResponseEntity<?> updatePopupStore(@RequestHeader("X-Auth-User") String email,
                                              @PathVariable Long popId,
                                              @RequestBody PopupStoreDto updatedDto) {
        return popupStoreService.updatePopupStore(email, popId, updatedDto);
    }

}
