package com.example.blog_example.model.dto.category.upper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class UpperCategorySaveDTO {
    @NotBlank
    private String name;
}
