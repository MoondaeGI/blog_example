package com.example.blog_example.model.dto.user.blog_visit_count;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Schema(description = "블로그 일일 방문자 수 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class BlogVisitCountDailyDTO {
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "example", required = true)
    @PositiveOrZero
    private Long userNo;
    @ApiModelProperty(name = "date", value = "날짜", example = "2000-01-01T00:00:00")
    @PastOrPresent
    private LocalDate date;
}
