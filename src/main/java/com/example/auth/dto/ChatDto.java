package com.example.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {
    private long chatId;

    private String sEmail;
    private String rEmail;
    private LocalDateTime created;
    private String content;
}
