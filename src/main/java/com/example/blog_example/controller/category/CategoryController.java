package com.example.blog_example.controller.category;

import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private UpperCategoryService upperCategoryService;
    private LowerCategoryService lowerCategoryService;
}
