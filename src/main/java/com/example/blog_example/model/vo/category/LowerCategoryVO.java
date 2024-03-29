package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "하위 카테고리 VO")
@NoArgsConstructor
@Getter
public class LowerCategoryVO {
    @ApiModelProperty(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long lowerCategoryNo;

    @ApiModelProperty(name = "name", value = "하위 카테고리 이름", example = "example", required = true)
    @NotBlank @Size(max = 10)
    private String name;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime modDt;

    private LowerCategoryVO(LowerCategory lowerCategory) {
        this.lowerCategoryNo = lowerCategory.getLowerCategoryNo();
        this.name = lowerCategory.getName();
        this.regDt = lowerCategory.getRegDt();
        this.modDt = lowerCategory.getModDt();
    }

    public static LowerCategoryVO from(LowerCategory lowerCategory) {
        return new LowerCategoryVO(lowerCategory);
    }
}
