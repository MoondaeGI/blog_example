package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.vo.user.UserVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "상위 카테고리 VO")
@NoArgsConstructor
@Getter
public class UpperCategoryVO {
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;

    @ApiModelProperty(name = "name", value = "상위 카테고리 이름", example = "example", required = true)
    @NotBlank @Max(10)
    private String name;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "userVO", value = "유저 VO", required = true)
    @Valid
    private UserVO userVO;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private  LocalDateTime modDt;

    private UpperCategoryVO(UpperCategory upperCategory) {
        this.upperCategoryNo = upperCategory.getUpperCategoryNo();
        this.name = upperCategory.getName();
        this.userVO = UserVO.from(upperCategory.getUser());
        this.regDt = upperCategory.getRegDt();
        this.modDt = upperCategory.getModDt();
    }

    public static UpperCategoryVO from(UpperCategory upperCategory) {
        return new UpperCategoryVO(upperCategory);
    }
}
