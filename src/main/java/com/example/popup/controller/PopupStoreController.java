package com.example.popup.controller;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.service.PopupStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/popup")
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    // 팝업스토어 개설 API (멀티파트 요청: JSON + 파일)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPopupStore(
            @RequestHeader("X-Auth-User") String email,
            @RequestPart("popupData") PopupCreationRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return popupStoreService.createPopupStore(email, request, file);
    }

    // 팝업스토어 전체 조회 API
    @GetMapping("")
    public ResponseEntity<?> getAllPopupStores() {
        return popupStoreService.getAllPopupStores();
    }

    // 자신이 올린 팝업스토어 수정 API
    @PutMapping("/{popId}")
    public ResponseEntity<?> updatePopupStore(
            @RequestHeader("X-Auth-User") String email,
            @PathVariable Long popId,
            @RequestBody PopupStoreDto updatedDto) {
        return popupStoreService.updatePopupStore(email, popId, updatedDto);
    }
}
