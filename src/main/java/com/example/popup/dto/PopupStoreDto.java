package com.example.popup.dto;

import lombok.Data;

@Data
public class PopupStoreDto {
    private String email;     // 소유자 이메일
    private String title;     // 스토어 이름
    private String content;   // 소개글
    private String start;     // 판매 시작 시간
    private String exp;       // 판매 종료 시간
    private Integer offline;  // 오프라인 여부
    private String address;   // 오프라인 주소
    private String category;  // 스토어 종류
    private String image;     // 썸네일 이미지 URL
    public PopupStoreDto(String email, String title, String content, String start, String exp, Integer offline, String address, String category, String image) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.start = start;
        this.exp = exp;
        this.offline = offline;
        this.address = address;
        this.category = category;
        this.image = image;
    }
}
