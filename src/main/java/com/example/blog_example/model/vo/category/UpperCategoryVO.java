package com.example.blog_example.model.vo.category;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UpperCategoryVO {
    @NotNull
    private Long upperCategoryNo;

    @NotNull
    private String name;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private  LocalDateTime modDt;

    private UpperCategoryVO(UpperCategory upperCategory) {
        this.upperCategoryNo = upperCategory.getUpperCategoryNo();
        this.name = upperCategory.getName();
        this.regDt = upperCategory.getRegDt();
        this.modDt = upperCategory.getModDt();
    }

    public static UpperCategoryVO from(UpperCategory upperCategory) {
        return new UpperCategoryVO(upperCategory);
    }
}
