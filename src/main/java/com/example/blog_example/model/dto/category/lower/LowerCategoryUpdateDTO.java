package com.example.blog_example.model.dto.category.lower;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LowerCategoryUpdateDTO {
    @NotNull
    private Long lowerCategoryNo;

    private String name;

    private Long upperCategoryNo;
}
