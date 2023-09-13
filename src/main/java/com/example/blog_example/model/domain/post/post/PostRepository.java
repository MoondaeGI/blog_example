package com.example.blog_example.model.domain.post.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findByUpperCategory(UpperCategory upperCategory, Pageable pageable);
    Page<Post> findByLowerCategory(LowerCategory lowerCategory, Pageable pageable);
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findAll(@Nullable Specification<Post> specification, Pageable pageable);
}
