package com.example.blog_example.model.domain.comment.liked;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikedRepository extends JpaRepository<CommentLiked, Long> {
    Long countByComment(Comment comment);
    void deleteByComment(Comment comment);
    Boolean existsByCommentAndUser(Comment comment, User user);
}
