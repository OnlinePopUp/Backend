package com.example.post.service;

import com.example.post.entity.Board;
import com.example.post.entity.Comment;
import com.example.post.entity.CommentHeart;
import com.example.post.jwt.JwtUtil;
import com.example.post.repository.BoardRepository;
import com.example.post.repository.CommentHeartRepository;
import com.example.post.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentHeartRepository commentHeartRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> write(String content, String email, long boardId) {
         Board board = boardRepository.findById(boardId).orElse(null);

         if(board == null)
             return ResponseEntity.badRequest().body("게시글이 존재하지 않습니다.");

         Comment comment = new Comment();
         comment.setContent(content);
         comment.setEmail(email);
         comment.setBoardId(boardId);

         commentRepository.save(comment);

         return ResponseEntity.ok("댓글이 작성되었습니다.");
    }

    public ResponseEntity<?> update(String token, long cmtId, String content) {
        Comment comment = commentRepository.findById(cmtId).orElse(null);

        if(comment == null)
            return ResponseEntity.badRequest().body("댓글이 존재하지 않습니다.");

        String email;
        try{
            email = jwtUtil.getEmail(token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("유효하지 않은 토큰");
        }

        if(!email.equals(comment.getEmail()))
            return ResponseEntity.badRequest().body("작성자만 수정할 수 있습니다.");

        comment.setContent(content);
        commentRepository.save(comment);

        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> delete(String token, long cmtId) {
        Comment comment = commentRepository.findById(cmtId).orElse(null);

        if(comment == null)
            return ResponseEntity.badRequest().body("댓글이 존재하지 않습니다.");

        String email,role;
        try{
            email = jwtUtil.getEmail(token);
            role = jwtUtil.getRole(token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("유효하지 않은 토큰");
        }

        if(!email.equals(comment.getEmail()) && !role.equals("ADMIN") )
            return ResponseEntity.badRequest().body("작성자 or 관리자만 삭제할 수 있습니다.");

        commentRepository.deleteById(cmtId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    public ResponseEntity<?> like(long cmtId, String email) {

        Comment comment = commentRepository.findById(cmtId).orElse(null);
        if(comment == null)
            return ResponseEntity.badRequest().body("존재하지 않는 댓글입니다.");

        Optional<CommentHeart> commentHeart = commentHeartRepository.findById(cmtId);

        if(commentHeart.isPresent())
            return ResponseEntity.badRequest().body("이미 좋아요 하신 상태입니다.");

        CommentHeart heart = new CommentHeart();
        heart.setEmail(email);
        heart.setCmtId(cmtId);
        commentHeartRepository.save(heart);


        comment.setHeart(comment.getHeart() + 1);
        commentRepository.save(comment);

        return ResponseEntity.ok("댓글 좋아요");
    }

    @Transactional
    public ResponseEntity<?> deleteLike(long cmtId, String email) {
        Comment comment = commentRepository.findById(cmtId).orElse(null);
        if(comment == null)
            return ResponseEntity.badRequest().body("존재하지 않는 댓글입니다.");

        Optional<CommentHeart> commentHeart = commentHeartRepository.findById(cmtId);

        if(!commentHeart.isPresent())
            return ResponseEntity.badRequest().body("좋아요 하신 상태가 아닙니다.");

        commentHeartRepository.deleteById(cmtId);

        comment.setHeart(comment.getHeart() - 1);
        commentRepository.save(comment);

        return ResponseEntity.ok("댓글 좋아요 취소");
    }
}
