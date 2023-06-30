package com.example.blog_example.service.comment;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.comment.comment.CommentRepository;
import com.example.blog_example.model.domain.comment.liked.CommentLiked;
import com.example.blog_example.model.domain.comment.liked.CommentLikedRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentLikedService {
    private final CommentLikedRepository commentLikedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Integer countByComment(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        return commentLikedRepository.countByComment(comment).intValue();
    }

    @Transactional
    public Long save(CommentLikedSaveDTO commentLikedSaveDTO) {
        Comment comment = commentRepository.findById(commentLikedSaveDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        User user = userRepository.findById(commentLikedSaveDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return commentLikedRepository.save(
                CommentLiked.builder()
                        .comment(comment)
                        .user(user)
                        .build())
                .getCommentLikedNo();
    }

    @Transactional
    public void delete(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentLikedRepository.deleteByComment(comment);
    }
}
