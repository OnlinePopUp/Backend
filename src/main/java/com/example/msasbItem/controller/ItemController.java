package com.example.msasbItem.controller;

import com.example.msasbItem.dto.ItemDto;
import com.example.msasbItem.service.AwsS3Service;
import com.example.msasbItem.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        List<String> imageUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = awsS3Service.upload(image);
                imageUrls.add(imageUrl);
            }
        }

        itemDto.setImageUrls(imageUrls);

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
    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemDto itemDto) {
        itemService.updateItem(itemId, itemDto);
        return ResponseEntity.ok().body(itemDto);
    }

    // 선택한 아이템 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
