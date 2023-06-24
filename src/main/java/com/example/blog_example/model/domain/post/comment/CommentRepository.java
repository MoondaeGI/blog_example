package com.example.blog_example.model.domain.post.comment;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostDetail(PostDetail postDetail);
    List<Comment> findByUser(User user);
}
