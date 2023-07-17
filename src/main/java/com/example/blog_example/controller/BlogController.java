package com.example.blog_example.controller;

import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.model.vo.user.BlogVisitCountVO;
import com.example.blog_example.service.user.BlogVisitCountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "블로그 방문 기록 조회", description = "해당 번호를 가진 유저의 오늘자 블로그 방문 기록을 조회하는 API")
    @Parameters({
            @Parameter(name = "visitorNo", description = "방문자 번호", example = "1", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "bloggerNo", description = "블로거 번호", example = "1", in = ParameterIn.QUERY, required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저(방문자)가 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저(블로거)가 없습니다.")
    })
    @GetMapping()
    public ResponseEntity<BlogVisitCountVO> find(
            @RequestParam(name = "visitor-no") @PositiveOrZero Long visitorNo,
            @RequestParam(name = "blogger-no") @PositiveOrZero Long bloggerNo){
        if (!blogVisitCountService.isVisit(visitorNo, bloggerNo))
            blogVisitCountService.addVisitCount(bloggerNo);

        return ResponseEntity.ok(blogVisitCountService.find(bloggerNo));
    }

    @Operation(summary = "블로그 누적 방문자 수 출력", description = "해당 유저 번호의 블로그의 누적 방문자 수를 출력하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/count/total")
    public ResponseEntity<Integer> countTotalVisit(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(blogVisitCountService.countTotalVisit(userNo));
    }

    @Operation(summary = "블로그 일일 방문자 수 출력", description = "해당 유저 번호의 블로그의 일일 방문자 수를 출력하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/count/daily")
    public ResponseEntity<Integer> countDailyVisit(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        LocalDate date = LocalDate.now();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = BlogVisitCountDailyDTO.builder()
                .userNo(userNo)
                .date(date)
                .build();

        return ResponseEntity.ok(blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO));
    }
}
