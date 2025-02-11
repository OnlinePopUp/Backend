package com.example.auth.contorller;

import com.example.auth.dto.UserDto;
import com.example.auth.service.AuthService;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(UserDto userDto) {
        return authService.adminJoin(userDto);
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers(int size, int page) {
        return userService.getAllUsers(size, page);
    }
}
