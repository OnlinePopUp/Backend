package com.example.msasbItem.controller;

import com.example.msasbItem.dto.ItemCreateDto;
import com.example.msasbItem.entity.ItemEntity;
import com.example.msasbItem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 아이템 등록
    @PostMapping
    public ResponseEntity<ItemCreateDto> createItem(
            @RequestHeader ("X-Auth-User") String email,
            @RequestBody ItemCreateDto itemCreateDto) {
        itemService.saveItem(email, itemCreateDto);
        return ResponseEntity.ok().body(itemCreateDto);
    }

    // 아이템 목록 조회
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        List<ItemEntity> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // 아이템 상세정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
        ItemEntity item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }
}
