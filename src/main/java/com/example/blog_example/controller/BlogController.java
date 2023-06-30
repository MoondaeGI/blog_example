package com.example.blog_example.controller;

import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.service.user.BlogVisitCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Validated
@RequiredArgsConstructor
@RequestMapping("/blog")
@Controller
public class BlogController {
    private final BlogVisitCountService blogVisitCountService;

    @GetMapping("/count")
    public Integer addVisitCount(@RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return blogVisitCountService.addVisitCount(userNo);
    }

    @GetMapping("/count/all")
    public Integer countAllVisit(@RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return blogVisitCountService.countAllVisitByUser(userNo);
    }

    @GetMapping("/count/daily")
    public Integer countDailyVisit(@RequestParam @PositiveOrZero Long userNo) {
        LocalDate date = LocalDate.now();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = BlogVisitCountDailyDTO.builder()
                .userNo(userNo)
                .date(date)
                .build();

        return blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO);
    }
}
