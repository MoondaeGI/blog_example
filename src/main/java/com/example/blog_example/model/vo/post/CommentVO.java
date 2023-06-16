package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    @PastOrPresent
    private LocalDateTime regDt;
    @PastOrPresent
    private LocalDateTime modDt;

    private CommentVO(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.content = comment.getContent();
        this.regDt = comment.getRegDt();
        this.modDt = comment.getModDt();
    }

    public static CommentVO from(Comment comment) { return new CommentVO(comment); }
}
