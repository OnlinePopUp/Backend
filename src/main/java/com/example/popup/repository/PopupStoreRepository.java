package com.example.popup.repository;

import com.example.popup.entity.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {
}
