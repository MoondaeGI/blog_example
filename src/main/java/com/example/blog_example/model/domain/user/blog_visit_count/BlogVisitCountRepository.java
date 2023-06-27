package com.example.blog_example.model.domain.user.blog_visit_count;

import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BlogVisitCountRepository extends JpaRepository<BlogVisitCount, Long> {
    BlogVisitCount findByUserAndDate(User user, LocalDate date);
    @Query("SELECT SUM(blogVisitCount.visitCount) " +
            "FROM BlogVisitCount blogVisitCount " +
            "WHERE blogVisitCount.user = :user")
    Long sumVisitCountByUser(User user);
}
