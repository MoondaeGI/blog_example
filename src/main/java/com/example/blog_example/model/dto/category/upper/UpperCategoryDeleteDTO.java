package com.example.blog_example.model.dto.category.upper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class UpperCategoryDeleteDTO {
    @Positive
    private Long upperCategoryNo;
}
