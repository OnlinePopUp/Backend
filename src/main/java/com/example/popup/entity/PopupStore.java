package com.example.popup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "popup_store")
public class PopupStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pop_id;  // 팝업스토어 고유 식별자

    @Column(unique = true, nullable = false)
    private String email;  // 소유자 이메일

    private String title;    // 스토어 이름
    private String content;  // 소개글
    private LocalDateTime start; // 판매 시작 시간
    private LocalDateTime exp;   // 판매 종료 시간
    private Integer offline; // 오프라인 여부
    private String address;  // 오프라인 주소
    private String category; // 스토어 종류
    private String image;    // 썸네일 이미지 URL

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }
}
