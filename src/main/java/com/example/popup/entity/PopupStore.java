package com.example.popup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "popup_store")
public class PopupStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long popupID;  // 팝업스토어 고유 식별자

    private String email;  // 소유자 이메일
    private String user;   // 스토어 이름

    private LocalDateTime saleStart; // 판매 시작 시간
    private LocalDateTime saleEnd;   // 판매 종료 시간

    @OneToMany(mappedBy = "popupStore", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    // 팝업스토어 개설 시 문자열로 받은 날짜를 LocalDateTime으로 변환하는 유틸리티
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }
}
