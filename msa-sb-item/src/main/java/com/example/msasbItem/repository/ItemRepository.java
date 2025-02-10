package com.example.msasbItem.repository;

import com.example.msasbItem.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    // 이메일을 기반으로 팝업 스토어 찾기
    List<ItemEntity> findByPopId(Long popId);
    ItemEntity findByPopIdAndItemId(Long popId, Long itemId);
}
