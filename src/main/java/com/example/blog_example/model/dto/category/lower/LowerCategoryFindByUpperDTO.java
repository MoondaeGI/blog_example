package com.example.blog_example.model.dto.category.lower;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LowerCategoryFindByUpperDTO {
    @NotNull
    private Long upperCategoryNo;
}
