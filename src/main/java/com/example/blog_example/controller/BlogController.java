package com.example.blog_example.controller;

import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.service.user.BlogVisitCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Api(tags = {"블로그 API"})
@Validated
@RequiredArgsConstructor
@RequestMapping("/blog")
@RestController
public class BlogController {
    private final BlogVisitCountService blogVisitCountService;

    @ApiOperation(value = "블로그 방문자 수 증가", notes = "해당 유저 번호의 블로그의 방문자 수를 증가시키는 API")
    @GetMapping("/count")
    public Integer addVisitCount(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return blogVisitCountService.addVisitCount(userNo);
    }

    @ApiOperation(value = "블로그 누적 방문자 수 출력", notes = "해당 유저 번호의 블로그의 누적 방문자 수를 출력하는 API")
    @GetMapping("/count/all")
    public Integer countAllVisit(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return blogVisitCountService.countAllVisitByUser(userNo);
    }

    @ApiOperation(value = "블로그 일일 방문자 수 출력", notes = "해당 유저 번호의 블로그의 일일 방문자 수를 출력하는 API")
    @GetMapping("/count/daily")
    public Integer countDailyVisit(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam @PositiveOrZero Long userNo) {
        LocalDate date = LocalDate.now();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = BlogVisitCountDailyDTO.builder()
                .userNo(userNo)
                .date(date)
                .build();

        return blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO);
    }
}
