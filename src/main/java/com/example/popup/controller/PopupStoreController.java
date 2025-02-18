package com.example.popup.controller;

import com.example.popup.dto.PopupCreationRequest;
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
    public ResponseEntity<?> createPopupStore(@RequestHeader("X-Auth-User") String email, @RequestBody PopupCreationRequest request) {
        return popupStoreService.createPopupStore(request);
    }
}
