package com.example.blog_example.model.dto.post.post;

import com.example.blog_example.util.annotation.valid.Enum;
import com.example.blog_example.util.enums.state.OpenState;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Schema(description = "게시글 등록 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class PostSaveDTO {
    @ApiModelProperty(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
    @PositiveOrZero
    private Long upperCategoryNo;

    @ApiModelProperty(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1")
    private Long lowerCategoryNo;

    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;

    @ApiModelProperty(name = "title", value = "제목", example = "example", required = true)
    @NotBlank @Size(max = 30)
    private String title;

    @ApiModelProperty(name = "content", value = "내용", example = "example", required = true)
    @NotBlank
    private String content;

    @ApiModelProperty
    @Enum(enumClass = OpenState.class, ignoreCase = true)
    private OpenState openState;
}
