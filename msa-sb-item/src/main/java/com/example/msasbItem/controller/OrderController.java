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
            @RequestHeader("X-Auth-User") String email,
            @RequestBody OrderDto orderDto) {
        orderService.orderItem(email, orderDto);
        return ResponseEntity.ok().body(orderDto);
    }

    // 주문 조회
    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getOrderItems(@RequestHeader("X-Auth-User") String email) {
        List<OrderDto> orderItem = orderService.getOrderItems(email);
        return ResponseEntity.ok().body(orderItem);
    }
}
