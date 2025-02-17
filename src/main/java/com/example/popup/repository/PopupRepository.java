package com.example.popup.repository;

import com.example.popup.entity.Popup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupRepository extends JpaRepository<Popup, Long> {
}
