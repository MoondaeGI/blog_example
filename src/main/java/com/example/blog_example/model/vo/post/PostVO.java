package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import com.example.blog_example.model.vo.user.UserVO;
import com.example.blog_example.util.enums.state.OpenState;
import com.example.blog_example.util.annotation.valid.Enum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "게시글 VO")
@NoArgsConstructor
@Getter
public class PostVO {
    @ApiModelProperty(name = "postNo", value = "게시글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long postNo;

    @ApiModelProperty(name = "userVO", value = "유저 VO", required = true)
    @Valid
    private UserVO userVO;

    @ApiModelProperty(name = "upperCategoryVO", value = "상위 카테고리 VO", required = true)
    @Valid
    private UpperCategoryVO upperCategoryVO;

    @ApiModelProperty(name = "lowerCategoryVO", value = "하위 카테고리 VO", required = true)
    @Valid
    private LowerCategoryVO lowerCategoryVO;

    @ApiModelProperty(name = "title", value = "제목", example = "example", required = true)
    @NotBlank @Max(30)
    private String title;

    @ApiModelProperty(name = "content", value = "내용", example = "example", required = true)
    @NotBlank
    private String content;

    @ApiModelProperty(name = "openYN", value = "공개 여부", example = "OPEN", required = true)
    @Enum(enumClass = OpenState.class, ignoreCase = true)
    private OpenState openState;

    @ApiModelProperty(name = "views", value = "조회수", example = "1", required = true)
    @PositiveOrZero
    private Integer views;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime modDt;

    private PostVO(Post post) {
        this.postNo = post.getPostNo();
        this.userVO = UserVO.from(post.getUser());
        this.upperCategoryVO = UpperCategoryVO.from(post.getUpperCategory());
        this.lowerCategoryVO = LowerCategoryVO.from(post.getLowerCategory());
        this.title = post.getTitle();
        this.content = post.getContent();
        this.openState = post.getOpenState();
        this.views = post.getViews();
        this.regDt = post.getRegDt();
        this.modDt = post.getModDt();
    }

    public static PostVO from(Post post) { return new PostVO(post); }
}
