package com.example.msasbItem.controller;

import com.example.msasbItem.dto.CartDto;
import com.example.msasbItem.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 아이템 담기
    @PostMapping("/{popId}/{itemId}")
    public ResponseEntity<CartDto> putCartItem(
            @RequestHeader("X-Auth-User") String email,
            @PathVariable Long popId,
            @PathVariable Long itemId,
            @RequestBody CartDto cartDto) {
        cartService.saveCartItem(email, popId, itemId, cartDto);
        return ResponseEntity.ok().body(cartDto);
    }

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<List<CartDto>> getCartItems(@RequestHeader("X-Auth-User") String email) {
        List<CartDto> cartItems = cartService.getCartItems(email);
        return ResponseEntity.ok(cartItems);
    }

    // 장바구니 아이템 빼기
    @PutMapping("/{popId}/{itemId}")
    public ResponseEntity<Void> decreaseCartItem(
            @RequestHeader("X-Auth-User") String email,
            @PathVariable Long popId,
            @PathVariable Long itemId,
            @RequestBody CartDto cartDto) {
        cartService.decreaseCartItem(email, popId, itemId, cartDto.getAmount());
        return ResponseEntity.ok().build();
    }
}
