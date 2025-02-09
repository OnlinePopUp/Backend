package com.example.msasbItem.repository;

import com.example.msasbItem.entity.PopUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PopUpRepository extends JpaRepository<PopUpEntity, Long> {
    // 이메일을 기반으로 팝업 스토어 찾기
    Optional<PopUpEntity> findByEmail(String email);
}
