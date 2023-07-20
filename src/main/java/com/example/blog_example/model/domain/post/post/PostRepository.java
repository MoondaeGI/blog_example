package com.example.blog_example.model.domain.post.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findByUpperCategory(UpperCategory upperCategory);
    List<Post> findByLowerCategory(LowerCategory lowerCategory);
    List<Post> findByUser(User user);
}
