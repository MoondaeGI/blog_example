package com.example.blog_example.model.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Schema(description = "댓글 수정 요청 DTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentUpdateDTO {
    @ApiModelProperty(name = "commentNo", value = "댓글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long commentNo;

    @ApiModelProperty(name = "content", value = "댓글 내용", example = "example", required = true)
    @NotBlank @Size(max = 150)
    private String content;
}
