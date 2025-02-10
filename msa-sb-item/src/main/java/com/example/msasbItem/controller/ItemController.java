package com.example.msasbItem.controller;

import com.example.msasbItem.dto.ItemDto;
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
    public ResponseEntity<ItemDto> createItem(
            @RequestHeader ("X-Auth-User") String email,
            @RequestBody ItemDto itemDto) {
        itemService.saveItem(email, itemDto);
        return ResponseEntity.ok().body(itemDto);
    }

    // 선택한 popId를 기반으로 아이템 보기
    @GetMapping("/{popId}")
    public ResponseEntity<List<ItemDto>> getAllItems(@PathVariable Long popId) {
        List<ItemDto> items = itemService.getAllItems(popId);
        return ResponseEntity.ok(items);
    }

    // 선택한 아이템 수정
    @PutMapping("/{popId}/{id}")
    public ResponseEntity<ItemDto> updateItem(
            @PathVariable Long popId,
            @PathVariable Long id,
            @RequestBody ItemDto itemDto) {
        itemService.updateItem(popId, id, itemDto);
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
