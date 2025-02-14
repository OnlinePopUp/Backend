package com.example.popup.dto;

import lombok.Data;

@Data
public class PopupStoreDto {
    private String email;  // 스토어 소유자 이메일
    private String user;   // 스토어 이름
    private String saleStart; // 판매 시작 (ISO 형식: "yyyy-MM-ddTHH:mm:ss")
    private String saleEnd;   // 판매 종료 (ISO 형식)
}
