package com.example.blog_example.model.dto.comment.comment;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Schema(description = "댓글 등록 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentSaveDTO {
    @ApiModelProperty(name = "content", value = "댓글 내용", example = "example", required = true)
    @NotBlank @Max(150)
    private String content;

    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;

    @ApiModelProperty(name = "postNo", value = "게시글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long postNo;
}
