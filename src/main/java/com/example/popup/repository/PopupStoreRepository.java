package com.example.popup.repository;

import com.example.popup.entity.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {
    // 소유자 이메일로 스토어 조회 등 추가 쿼리 메서드 작성 가능
}
