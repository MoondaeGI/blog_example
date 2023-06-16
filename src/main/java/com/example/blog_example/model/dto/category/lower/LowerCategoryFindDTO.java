package com.example.blog_example.model.dto.category.lower;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class LowerCategoryFindDTO {
    @Positive
    private Long lowerCategoryNo;
}
