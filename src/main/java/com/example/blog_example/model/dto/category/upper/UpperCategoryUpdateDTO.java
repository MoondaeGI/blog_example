package com.example.blog_example.model.dto.category.upper;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Schema(description = "상위 카테고리 수정 요청 DTO")
@AllArgsConstructor
@Getter
public class UpperCategoryUpdateDTO {
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;
    @ApiModelProperty(name = "name", value = "상위 카테고리 이름", example = "example", required = true)
    @NotBlank @Max(10)
    private String name;
}
