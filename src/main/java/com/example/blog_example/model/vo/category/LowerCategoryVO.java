package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class LowerCategoryVO {
    @NotNull
    private Long lowerCategoryNo;

    @NotNull
    private String name;

    @NotNull
    private UpperCategory upperCategory;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private LowerCategoryVO(LowerCategory lowerCategory) {
        this.lowerCategoryNo = lowerCategory.getLowerCategoryNo();
        this.name = lowerCategory.getName();
        this.upperCategory = lowerCategory.getUpperCategory();
        this.regDt = lowerCategory.getRegDt();
        this.modDt = lowerCategory.getModDt();
    }

    public static LowerCategoryVO from(LowerCategory lowerCategory) {
        return new LowerCategoryVO(lowerCategory);
    }
}
