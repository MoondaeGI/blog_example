package com.example.blog_example.model.dto.category.upper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class UpperCategoryUpdateDTO {
    @Positive
    private Long upperCategoryNo;
    @NotBlank
    private String name;
}
