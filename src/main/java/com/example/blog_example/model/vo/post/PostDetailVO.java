package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@Getter
public class PostDetailVO {
    @Positive
    private Long postDetailNo;

    @NotBlank
    private String title;

    @NotNull
    private String content;

    @PositiveOrZero
    private Integer views;

    private PostDetailVO(PostDetail postDetail) {
        this.postDetailNo = postDetail.getPostDetailNo();
        this.title = postDetail.getTitle();
        this.content = postDetail.getContent();
        this.views = postDetail.getViews();
    }

    public static PostDetailVO from(PostDetail postDetail) { return new PostDetailVO(postDetail); }
}
