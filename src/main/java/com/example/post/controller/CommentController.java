package com.example.post.controller;

import com.example.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/write")
    public ResponseEntity<?> write(String content, String email, long boardId) {
        return commentService.write(content,email,boardId);
    }
    // 업데이트 딜리트 좋아요 좋취

    @PostMapping("/update/{cmtId}") // jwt로 이메일 검증
    public ResponseEntity<?> update(@PathVariable long cmtId, String content) {
        return commentService.update(cmtId, content);
    }

    @PostMapping("/delete/{cmtId}")
    public ResponseEntity<?> delete(@PathVariable long cmtId) {
        return commentService.delete(cmtId);
    }

    @PostMapping("/like/{cmtId}")
    public ResponseEntity<?> like(@PathVariable long cmtId, String email) {
        return commentService.like(cmtId,email);
    }

    @PostMapping("/deletelike/{cmtId}")
    public ResponseEntity<?> deleteLike(@PathVariable long cmtId, String email) {
        return commentService.deleteLike(cmtId,email);
    }

}
