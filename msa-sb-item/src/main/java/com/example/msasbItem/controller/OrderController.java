package com.example.msasbItem.controller;

import com.example.msasbItem.dto.CartDto;
import com.example.msasbItem.dto.ItemDto;
import com.example.msasbItem.dto.OrderDto;
import com.example.msasbItem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 하기
    @PostMapping
    public ResponseEntity<OrderDto> createItem(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderDto orderDto) {
        orderService.orderItem(token, orderDto);
        return ResponseEntity.ok().body(orderDto);
    }

    // 주문 조회
    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getOrderItems(@RequestHeader("Authorization") String token) {
        List<OrderDto> orderItem = orderService.getOrderItems(token);
        return ResponseEntity.ok().body(orderItem);
    }
}
