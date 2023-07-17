package com.example.blog_example.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Schema(name = "블로그 방문 기록 VO")
@NoArgsConstructor
@Getter
public class BlogVisitCountVO {
    @ApiModelProperty(name = "totalVisitCount", value = "전체 방문자 수", example = "1", required = true)
    @PositiveOrZero
    private Integer totalVisitCount;

    @ApiModelProperty(name = "dailyVisitCount", value = "일일 방문자 수", example = "1", required = true)
    @PositiveOrZero
    private Integer dailyVisitCount;

    private BlogVisitCountVO(Integer totalVisitCount, Integer dailyVisitCount) {
        this.totalVisitCount = totalVisitCount;
        this.dailyVisitCount = dailyVisitCount;
    }

    @Builder
    public static BlogVisitCountVO of(Integer totalVisitCount, Integer dailyVisitCount) {
        return new BlogVisitCountVO(totalVisitCount, dailyVisitCount);
    }
}
