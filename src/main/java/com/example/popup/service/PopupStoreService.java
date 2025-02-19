package com.example.popup.service;

import com.example.popup.dto.PopupCreationRequest;
import com.example.popup.dto.PopupStoreDto;
import com.example.popup.entity.PopupStore;
import com.example.popup.repository.PopupStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final AwsS3Service awsS3Service; // S3 업로드 서비스 주입

    // 팝업스토어 개설 API (파일 첨부 옵션 포함)
    public ResponseEntity<?> createPopupStore(String email, PopupCreationRequest request, MultipartFile file) {
        PopupStoreDto storeDto = request.getPopupStoreDto();

        // 기본적으로 전달된 image 필드 값 사용하지만,
        // 파일이 첨부되면 S3에 업로드 후 반환된 URL로 대체합니다.
        String imageUrl = storeDto.getImage();
        if (file != null && !file.isEmpty()) {
            imageUrl = awsS3Service.upload(file);
        }

        PopupStore popupStore = PopupStore.builder()
                .email(email)
                .title(storeDto.getTitle())
                .content(storeDto.getContent())
                .start(PopupStore.parseDateTime(storeDto.getStart()))
                .exp(PopupStore.parseDateTime(storeDto.getExp()))
                .offline(storeDto.getOffline())
                .address(storeDto.getAddress())
                .category(storeDto.getCategory())
                .image(imageUrl)
                .build();

        popupStoreRepository.save(popupStore);
        return ResponseEntity.ok("팝업스토어 개설이 완료되었습니다.");
    }

    // 팝업스토어 전체 조회 API
    public ResponseEntity<?> getAllPopupStores() {
        return ResponseEntity.ok(popupStoreRepository.findAll());
    }

    // 자신이 올린 팝업스토어 수정 API
    public ResponseEntity<?> updatePopupStore(String email, Long popId, PopupStoreDto updatedDto) {
        return popupStoreRepository.findById(popId)
                .map(popupStore -> {
                    if (!popupStore.getEmail().equals(email)) {
                        return ResponseEntity.status(403).body("수정 권한이 없습니다.");
                    }
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
                })
                .orElseGet(() -> ResponseEntity.status(404).body("팝업스토어를 찾을 수 없습니다."));
    }
}
