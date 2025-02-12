package com.example.msasbItem.repository;

import com.example.msasbItem.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByPopId(Long popId);
    ItemEntity findByPopIdAndItemId(Long popId, Long itemId);
    List<ItemEntity> findListByPopIdAndItemId(Long popId, Long itemId);
}
