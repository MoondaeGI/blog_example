package com.example.blog_example.model.domain.user.blog_visit_count;

import com.example.blog_example.model.domain.user.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_BLOG_VISIT_COUNT")
public class BlogVisitCount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOG_VISIT_COUNT_NO")
    private Long blogVisitCountNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "VISIT_COUNT")
    private Integer visitCount;

    @Builder
    public BlogVisitCount(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        this.visitCount = 0;
    }

    public Integer addVisitCount() {
        return this.visitCount += 1;
    }
}
