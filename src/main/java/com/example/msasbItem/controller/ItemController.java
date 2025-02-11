package com.example.msasbItem.controller;

import com.example.msasbItem.dto.ItemDto;
import com.example.msasbItem.service.AwsS3Service;
import com.example.msasbItem.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final AwsS3Service awsS3Service;

    // 아이템 등록
    @PostMapping
    public ResponseEntity<ItemDto> createItem(
            @RequestHeader("Authorization") String token,
            @RequestPart("itemDto") ItemDto itemDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        // 이미지 업로드 후 URL 받기
        if (image != null && !image.isEmpty()) {
            String imageUrl = awsS3Service.upload(image);
            itemDto.setImageUrl(imageUrl); // 이미지 URL을 DTO에 저장
        }

        // 아이템 저장
        itemService.saveItem(token, itemDto);

        return ResponseEntity.ok().body(itemDto);
    }

    // 선택한 popId를 기반으로 아이템 보기
    @GetMapping("/{popId}")
    public ResponseEntity<List<ItemDto>> getAllItems(@PathVariable Long popId) {
        List<ItemDto> items = itemService.getAllItems(popId);
        return ResponseEntity.ok(items);
    }

    // 선택한 아이템 수정
    @PutMapping("/{popId}/{itemId}")
    public ResponseEntity<ItemDto> updateItem(
            @PathVariable Long popId,
            @PathVariable Long itemId,
            @RequestBody ItemDto itemDto) {
        itemService.updateItem(popId, itemId, itemDto);
        return ResponseEntity.ok().body(itemDto);
    }

    // 선택한 아이템 삭제
    @DeleteMapping("/{popId}/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long popId,
            @PathVariable Long id) {
        itemService.deleteItem(popId, id);
        return ResponseEntity.noContent().build();
    }
}
