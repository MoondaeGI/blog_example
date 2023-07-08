package com.example.blog_example.model.domain.comment.liked;

import com.example.blog_example.model.domain.comment.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikedRepository extends JpaRepository<CommentLiked, Long> {
    CommentLiked findByComment(Comment comment);
    Long countByComment(Comment comment);
    void deleteByComment(Comment comment);
    Boolean existsByComment(Comment comment);
}
