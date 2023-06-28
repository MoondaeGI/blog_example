package com.example.blog_example.model.domain.user.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByName(String name);
    Boolean existsByBlogName(String blogName);
}
