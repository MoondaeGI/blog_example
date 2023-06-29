package com.example.blog_example.controller;

import com.example.blog_example.model.dto.category.lower.*;
import com.example.blog_example.model.dto.category.upper.UpperCategoryDeleteDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryFindDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/category")
@Controller
public class CategoryController {
    private final UpperCategoryService upperCategoryService;
    private final LowerCategoryService lowerCategoryService;

    @GetMapping("/upper/info/list")
    public List<UpperCategoryVO> upperCategoryFindAll() {
        return upperCategoryService.findAll();
    }

    @GetMapping("/lower/info/list")
    public List<LowerCategoryVO> lowerCategoryFindAll() {
        return lowerCategoryService.findAll();
    }

    @GetMapping("/upper/info")
    public UpperCategoryVO upperCategoryFind(@RequestParam(name = "no") Long upperCategoryNo) {
        UpperCategoryFindDTO upperCategoryFindDTO = UpperCategoryFindDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .build();

        return upperCategoryService.find(upperCategoryFindDTO);
    }

    @GetMapping("/lower/info")
    public LowerCategoryVO lowerCategoryFind(@RequestParam(name = "no") Long lowerCategoryNo) {
        LowerCategoryFindDTO lowerCategoryFindDTO = LowerCategoryFindDTO.builder()
                .lowerCategoryNo(lowerCategoryNo)
                .build();

        return lowerCategoryService.find(lowerCategoryFindDTO);
    }

    @GetMapping("/lower/info/upper")
    public List<LowerCategoryVO> lowerCategoryFindByUpper(@RequestParam(name = "no") Long upperCategoryNo) {
        LowerCategoryFindByUpperDTO lowerCategoryFindByUpperDTO = LowerCategoryFindByUpperDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .build();

        return lowerCategoryService.findByUpperCategory(lowerCategoryFindByUpperDTO);
    }

    @PostMapping("/upper/save")
    public Long upperCategorySave(@RequestBody @Valid UpperCategorySaveDTO upperCategorySaveDTO) {
        return upperCategoryService.save(upperCategorySaveDTO);
    }

    @PostMapping("/lower/save")
    public Long lowerCategorySave(@RequestBody @Valid LowerCategorySaveDTO lowerCategorySaveDTO) {
        return lowerCategoryService.save(lowerCategorySaveDTO);
    }

    @PutMapping("/upper/update")
    public Long upperCategoryUpdate(@RequestBody @Valid UpperCategoryUpdateDTO upperCategoryUpdateDTO) {
        return upperCategoryService.update(upperCategoryUpdateDTO);
    }

    @PutMapping("/lower/update")
    public Long lowerCategoryUpdate(@RequestBody @Valid LowerCategoryUpdateDTO lowerCategoryUpdateDTO) {
        return lowerCategoryService.update(lowerCategoryUpdateDTO);
    }

    @DeleteMapping("/upper/delete")
    public void upperCategoryDelete(@RequestParam(name = "no") Long upperCategoryNo) {
        UpperCategoryDeleteDTO upperCategoryDeleteDTO = UpperCategoryDeleteDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .build();

        upperCategoryService.delete(upperCategoryDeleteDTO);
    }

    @DeleteMapping("/lower/delete")
    public void lowerCategoryDelete(@RequestParam(name = "no") Long lowerCategoryNo) {
        LowerCategoryDeleteDTO lowerCategoryDeleteDTO = LowerCategoryDeleteDTO.builder()
                .lowerCategoryNo(lowerCategoryNo)
                .build();

        lowerCategoryService.delete(lowerCategoryDeleteDTO);
    }
}
