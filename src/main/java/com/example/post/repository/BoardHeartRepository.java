package com.example.post.repository;

import com.example.post.entity.BoardHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardHeartRepository extends JpaRepository<BoardHeart, Long> {
    Optional<BoardHeart> findByBoardIdAndEmail(long boardId, String email);
}
