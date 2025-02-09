package com.example.msasbItem.repository;

import com.example.msasbItem.entity.ItemEntity;
import com.example.msasbItem.entity.PopUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    // 이메일을 기반으로 팝업 스토어 찾기
    List<ItemEntity> findByPopId(Long popId);
}
