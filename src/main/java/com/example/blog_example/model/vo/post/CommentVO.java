package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.comment.Comment;
import com.example.blog_example.model.vo.user.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentVO {
    @Positive
    private Long commentNo;

    @NotBlank
    private String content;

    @NotNull
    private UserVO userVO;

    @NotNull
    private PostDetailVO postDetailVO;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private CommentVO(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.content = comment.getContent();
        this.userVO = UserVO.from(comment.getUser());
        this.postDetailVO = PostDetailVO.from(comment.getPostDetail());
        this.regDt = comment.getRegDt();
        this.modDt = comment.getModDt();
    }

    public static CommentVO from(Comment comment) { return new CommentVO(comment); }
}
