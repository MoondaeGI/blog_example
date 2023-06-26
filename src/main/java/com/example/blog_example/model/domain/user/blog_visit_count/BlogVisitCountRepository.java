package com.example.blog_example.model.domain.user.blog_visit_count;

import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BlogVisitCountRepository extends JpaRepository<BlogVisitCount, Long> {
    Long countByUser(User user);
    BlogVisitCount findByUserAndDate(User user, LocalDate date);
}
