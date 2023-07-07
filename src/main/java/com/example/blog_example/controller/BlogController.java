package com.example.blog_example.controller;

import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.service.user.BlogVisitCountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Tag(name = "blog", description = "블로그 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/blog")
@RestController
public class BlogController {
    private final BlogVisitCountService blogVisitCountService;

    @Operation(summary = "블로그 방문자 수 증가", description = "해당 유저 번호의 블로그의 방문자 수를 증가시키는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/count")
    public ResponseEntity<Integer> addVisitCount(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(blogVisitCountService.addVisitCount(userNo));
    }

    @Operation(summary = "블로그 누적 방문자 수 출력", description = "해당 유저 번호의 블로그의 누적 방문자 수를 출력하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/count/all")
    public ResponseEntity<Integer> countAllVisit(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(blogVisitCountService.countAllVisitByUser(userNo));
    }

    @Operation(summary = "블로그 일일 방문자 수 출력", description = "해당 유저 번호의 블로그의 일일 방문자 수를 출력하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/count/daily")
    public ResponseEntity<Integer> countDailyVisit(
            @RequestParam @PositiveOrZero Long userNo) {
        LocalDate date = LocalDate.now();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = BlogVisitCountDailyDTO.builder()
                .userNo(userNo)
                .date(date)
                .build();

        return ResponseEntity.ok(blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO));
    }
}
