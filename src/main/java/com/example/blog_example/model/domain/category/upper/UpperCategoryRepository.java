package com.example.blog_example.model.domain.category.upper;

import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpperCategoryRepository extends JpaRepository<UpperCategory, Long> {
    List<UpperCategory> findByUser(User user);
}
