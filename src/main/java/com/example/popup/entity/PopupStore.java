package com.example.popup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "popup_store")
public class PopupStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long popupID;  // 팝업 스토어 고유 식별자

    private String email;  // 스토어 소유자 이메일
    private String name;   // 스토어 이름

    // 추가 필드 예시: 스토어 설명, 생성일, 판매 기간 등
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime saleStart;
    private LocalDateTime saleEnd;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}
