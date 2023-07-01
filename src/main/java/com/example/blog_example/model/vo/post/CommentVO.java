package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.vo.user.UserVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "댓글 VO")
@NoArgsConstructor
@Getter
public class CommentVO {
    @ApiModelProperty(name = "commentNo", value = "댓글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long commentNo;

    @ApiModelProperty(name = "content", value = "댓글 내용", example = "example", required = true)
    @NotBlank @Max(150)
    private String content;

    @ApiModelProperty(name = "userVO", value = "유저 VO", required = true)
    @NotNull
    private UserVO userVO;

    @ApiModelProperty(name = "postVO", value = "게시글 VO", required = true)
    @NotNull
    private PostVO postVO;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime modDt;

    private CommentVO(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.content = comment.getContent();
        this.userVO = UserVO.from(comment.getUser());
        this.postVO = PostVO.from(comment.getPost());
        this.regDt = comment.getRegDt();
        this.modDt = comment.getModDt();
    }

    public static CommentVO from(Comment comment) { return new CommentVO(comment); }
}
