package com.example.post.repository;

import com.example.post.entity.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
}
