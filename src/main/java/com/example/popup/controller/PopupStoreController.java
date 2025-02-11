package com.example.popup.controller;

import com.example.popup.service.PopupStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popup-store")
public class PopupStoreController {
    private final PopupStoreService popupStoreService;

    // 팝업 스토어 개설 API
    @PostMapping("/create")
    public ResponseEntity<?> createStore(@RequestParam String storeName,
                                         @RequestParam String description,
                                         @RequestParam String ownerEmail,
                                         @RequestParam String saleStart, // "yyyy-MM-ddTHH:mm:ss" 형식으로 받기
                                         @RequestParam String saleEnd) {
        LocalDateTime start = LocalDateTime.parse(saleStart);
        LocalDateTime end = LocalDateTime.parse(saleEnd);
        return popupStoreService.createStore(storeName, description, ownerEmail, start, end);
    }

    // 팝업 스토어 조회 API
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStore(@PathVariable Long storeId) {
        return popupStoreService.getStore(storeId);
    }
}
