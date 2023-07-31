package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "상위 카테고리 VO")
@NoArgsConstructor
@Getter
public class UpperCategoryVO {
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;

    @ApiModelProperty(name = "name", value = "상위 카테고리 이름", example = "example", required = true)
    @NotBlank @Size(max = 10)
    private String name;

    @ApiModelProperty(name = "lowerCategoryVOList", value = "하위 카테고리 목록", required = true)
    @Valid
    private List<LowerCategoryVO> lowerCategoryVOList;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private  LocalDateTime modDt;

    private List<LowerCategoryVO> setLowerCategoryVOList(UpperCategory upperCategory) {
        List<LowerCategoryVO> lowerCategoryList = upperCategory.getLowerCategoryList().stream()
                .map(LowerCategoryVO::from)
                .collect(Collectors.toList());
        lowerCategoryList.remove(0);

        return lowerCategoryList;
    }

    private UpperCategoryVO(UpperCategory upperCategory) {
        this.upperCategoryNo = upperCategory.getUpperCategoryNo();
        this.name = upperCategory.getName();
        this.lowerCategoryVOList = setLowerCategoryVOList(upperCategory);
        this.regDt = upperCategory.getRegDt();
        this.modDt = upperCategory.getModDt();
    }

    public static UpperCategoryVO from(UpperCategory upperCategory) {
        return new UpperCategoryVO(upperCategory);
    }
}
