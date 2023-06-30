package com.example.blog_example.controller;

import com.example.blog_example.model.dto.category.lower.LowerCategorySaveDTO;
import com.example.blog_example.model.dto.category.lower.LowerCategoryUpdateDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Api(tags = {"카테고리 API"})
@Validated
@RequiredArgsConstructor
@RequestMapping("/category")
@Controller
public class CategoryController {
    private final UpperCategoryService upperCategoryService;
    private final LowerCategoryService lowerCategoryService;

    @ApiOperation(value = "모든 상위 카테고리 검색", notes = "데이터 베이스 내부의 모든 상위 카테고리를 검색하는 API")
    @GetMapping("/upper/info/list")
    public List<UpperCategoryVO> upperCategoryFindAll() {
        return upperCategoryService.findAll();
    }

    @ApiOperation(value = "모든 하위 카테고리 검색", notes = "데이터 베이스 내부의 모든 하위 카테고리를 검색하는 API")
    @GetMapping("/lower/info/list")
    public List<LowerCategoryVO> lowerCategoryFindAll() {
        return lowerCategoryService.findAll();
    }

    @ApiOperation(value = "상위 카테고리 검색", notes = "해당 상위 카테고리 번호를 가진 상위 카테고리를 검색하는 API")
    @GetMapping("/upper/info")
    public UpperCategoryVO upperCategoryFind(
            @ApiParam(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return upperCategoryService.find(upperCategoryNo);
    }

    @ApiOperation(value = "하위 카테고리 검색", notes = "해당 하위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @GetMapping("/lower/info")
    public LowerCategoryVO lowerCategoryFind(
            @ApiParam(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return lowerCategoryService.find(lowerCategoryNo);
    }

    @ApiOperation(value = "상위 카테고리로 하위 카테고리 검색", notes = "해당 상위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @GetMapping("/lower/info/upper")
    public List<LowerCategoryVO> lowerCategoryFindByUpper(
            @ApiParam(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return lowerCategoryService.findByUpperCategory(upperCategoryNo);
    }

    @ApiOperation(value = "상위 카테고리 등록", notes = "DTO를 받아 상위 카테고리를 등록하는 API")
    @PostMapping("/upper/save")
    public Long upperCategorySave(@RequestBody UpperCategorySaveDTO upperCategorySaveDTO) {
        Long upperCategoryNo = upperCategoryService.save(upperCategorySaveDTO);

        LowerCategorySaveDTO lowerCategorySaveDTO = LowerCategorySaveDTO.builder()
                .name("전체")
                .upperCategoryNo(upperCategoryNo)
                .build();
        lowerCategoryService.save(lowerCategorySaveDTO);

        return upperCategoryNo;
    }

    @ApiOperation(value = "하위 카테고리 등록", notes = "DTO를 받아 하위 카테고리를 등록하는 API")
    @PostMapping("/lower/save")
    public Long lowerCategorySave(@RequestBody LowerCategorySaveDTO lowerCategorySaveDTO) {
        return lowerCategoryService.save(lowerCategorySaveDTO);
    }

    @ApiOperation(value = "상위 카테고리 수정", notes = "DTO를 받아 상위 카테고리를 수정하는 API")
    @PutMapping("/upper/update")
    public Long upperCategoryUpdate(@RequestBody UpperCategoryUpdateDTO upperCategoryUpdateDTO) {
        return upperCategoryService.update(upperCategoryUpdateDTO);
    }

    @ApiOperation(value = "하위 카테고리 수정", notes = "DTO를 받아 하위 카테고리를 수정하는 API")
    @PutMapping("/lower/update")
    public Long lowerCategoryUpdate(@RequestBody LowerCategoryUpdateDTO lowerCategoryUpdateDTO) {
        return lowerCategoryService.update(lowerCategoryUpdateDTO);
    }

    @ApiOperation(value = "상위 카테고리 삭제", notes = "해당 상위 카테고리 번호를 가진 상위 카테고리를 삭제하는 API")
    @DeleteMapping("/upper/delete")
    public void upperCategoryDelete(
            @ApiParam(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        upperCategoryService.delete(upperCategoryNo);
    }

    @ApiOperation(value = "하위 카테고리 삭제", notes = "해당 하위 카테고리 번호를 가진 하위 카테고리를 삭제하는 API")
    @DeleteMapping("/lower/delete")
    public void lowerCategoryDelete(
            @ApiParam(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        lowerCategoryService.delete(lowerCategoryNo);
    }
}
