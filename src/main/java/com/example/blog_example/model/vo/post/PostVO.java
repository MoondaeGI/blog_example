package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostVO {
    @Positive
    private Long postNo;

    @NotNull
    private User user;

    @NotNull
    private UpperCategory upperCategory;

    @NotNull
    private LowerCategory lowerCategory;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private PostVO(Post post) {
        this.postNo = post.getPostNo();
        this.user = post.getUser();
        this.upperCategory = post.getUpperCategory();
        this.lowerCategory = post.getLowerCategory();
        this.regDt = post.getRegDt();
        this.modDt = post.getModDt();
    }

    public static PostVO from(Post post) { return new PostVO(post); }
}
