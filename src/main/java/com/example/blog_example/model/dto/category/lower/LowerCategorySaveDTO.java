package com.example.blog_example.model.dto.category.lower;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class LowerCategorySaveDTO {
    @NotBlank
    private String name;
    @Positive
    private Long upperCategoryNo;
}
