package com.example.blog_example.controller;

import com.example.blog_example.model.dto.category.lower.LowerCategorySaveDTO;
import com.example.blog_example.model.dto.category.lower.LowerCategoryUpdateDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "category", description = "카테고리 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {
    private final UpperCategoryService upperCategoryService;
    private final LowerCategoryService lowerCategoryService;

    @Operation(summary = "모든 상위 카테고리 검색", description = "데이터 베이스 내부의 모든 상위 카테고리를 검색하는 API")
    @GetMapping("/upper/list")
    public List<UpperCategoryVO> upperCategoryFindAll() {
        return upperCategoryService.findAll();
    }

    @Operation(summary = "모든 하위 카테고리 검색", description = "데이터 베이스 내부의 모든 하위 카테고리를 검색하는 API")
    @GetMapping("/lower/list")
    public List<LowerCategoryVO> lowerCategoryFindAll() {
        return lowerCategoryService.findAll();
    }

    @Operation(summary = "상위 카테고리 검색", description = "해당 상위 카테고리 번호를 가진 상위 카테고리를 검색하는 API")
    @GetMapping("/upper")
    public UpperCategoryVO upperCategoryFind(
            @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return upperCategoryService.find(upperCategoryNo);
    }

    @Operation(summary = "하위 카테고리 검색", description = "해당 하위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @GetMapping("/lower")
    public LowerCategoryVO lowerCategoryFind(
            @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return lowerCategoryService.find(lowerCategoryNo);
    }

    @Operation(summary = "상위 카테고리로 하위 카테고리 검색", description = "해당 상위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @GetMapping("/lower/list/upper")
    public List<LowerCategoryVO> lowerCategoryFindByUpper(
            @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return lowerCategoryService.findByUpperCategory(upperCategoryNo);
    }

    @Operation(summary = "상위 카테고리 등록", description = "DTO를 받아 상위 카테고리를 등록하는 API")
    @PostMapping("/upper")
    public Long upperCategorySave(@RequestBody UpperCategorySaveDTO upperCategorySaveDTO) {
        Long upperCategoryNo = upperCategoryService.save(upperCategorySaveDTO);

        LowerCategorySaveDTO lowerCategorySaveDTO = LowerCategorySaveDTO.builder()
                .name("전체")
                .upperCategoryNo(upperCategoryNo)
                .build();
        lowerCategoryService.save(lowerCategorySaveDTO);

        return upperCategoryNo;
    }

    @Operation(summary = "하위 카테고리 등록", description = "DTO를 받아 하위 카테고리를 등록하는 API")
    @PostMapping("/lower")
    public Long lowerCategorySave(@RequestBody LowerCategorySaveDTO lowerCategorySaveDTO) {
        return lowerCategoryService.save(lowerCategorySaveDTO);
    }

    @Operation(summary = "상위 카테고리 수정", description = "DTO를 받아 상위 카테고리를 수정하는 API")
    @PutMapping("/upper/update")
    public Long upperCategoryUpdate(@RequestBody UpperCategoryUpdateDTO upperCategoryUpdateDTO) {
        return upperCategoryService.update(upperCategoryUpdateDTO);
    }

    @Operation(summary = "하위 카테고리 수정", description = "DTO를 받아 하위 카테고리를 수정하는 API")
    @PutMapping("/lower")
    public Long lowerCategoryUpdate(@RequestBody LowerCategoryUpdateDTO lowerCategoryUpdateDTO) {
        return lowerCategoryService.update(lowerCategoryUpdateDTO);
    }

    @Operation(summary = "상위 카테고리 삭제", description = "해당 상위 카테고리 번호를 가진 상위 카테고리를 삭제하는 API")
    @DeleteMapping("/upper")
    public void upperCategoryDelete(
            @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        upperCategoryService.delete(upperCategoryNo);
    }

    @Operation(summary = "하위 카테고리 삭제", description = "해당 하위 카테고리 번호를 가진 하위 카테고리를 삭제하는 API")
    @DeleteMapping("/lower")
    public void lowerCategoryDelete(
            @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        lowerCategoryService.delete(lowerCategoryNo);
    }
}
