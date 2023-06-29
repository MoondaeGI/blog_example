package com.example.blog_example.model.dto.category.lower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@Getter
public class LowerCategoryDeleteDTO {
    @Positive
    private Long lowerCategoryNo;
}
