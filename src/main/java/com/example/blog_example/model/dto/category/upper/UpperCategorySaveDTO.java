package com.example.blog_example.model.dto.category.upper;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Schema(description = "상위 카테고리 등록 요청 DTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpperCategorySaveDTO {
    @ApiModelProperty(name = "name", value = "상위 카테고리 이름", example = "example", required = true)
    @NotBlank @Size(max = 10)
    private String name;

    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;
}
