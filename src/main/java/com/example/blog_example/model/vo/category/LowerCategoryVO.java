package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class LowerCategoryVO {
    @Positive
    private Long lowerCategoryNo;

    @NotBlank
    private String name;

    @NotNull
    private UpperCategoryVO upperCategoryVO;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private LowerCategoryVO(LowerCategory lowerCategory) {
        this.lowerCategoryNo = lowerCategory.getLowerCategoryNo();
        this.name = lowerCategory.getName();
        this.upperCategoryVO = UpperCategoryVO.from(lowerCategory.getUpperCategory());
        this.regDt = lowerCategory.getRegDt();
        this.modDt = lowerCategory.getModDt();
    }

    public static LowerCategoryVO from(LowerCategory lowerCategory) {
        return new LowerCategoryVO(lowerCategory);
    }
}
