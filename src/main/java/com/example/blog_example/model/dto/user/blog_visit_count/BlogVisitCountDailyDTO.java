package com.example.blog_example.model.dto.user.blog_visit_count;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BlogVisitCountDailyDTO {
    @Positive
    private Long userNo;
    @PastOrPresent
    private LocalDate date;
}