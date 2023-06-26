package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import com.example.blog_example.model.vo.user.UserVO;
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
    private UserVO userVO;

    @NotNull
    private UpperCategoryVO upperCategoryVO;

    @NotNull
    private LowerCategoryVO lowerCategoryVO;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private PostVO(Post post) {
        this.postNo = post.getPostNo();
        this.userVO = UserVO.from(post.getUser());
        this.upperCategoryVO = UpperCategoryVO.from(post.getUpperCategory());
        this.lowerCategoryVO = LowerCategoryVO.from(post.getLowerCategory());
        this.regDt = post.getRegDt();
        this.modDt = post.getModDt();
    }

    public static PostVO from(Post post) { return new PostVO(post); }
}
