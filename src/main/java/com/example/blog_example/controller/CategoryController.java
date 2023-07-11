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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "유저가 가진 모든 상위 카테고리 검색", description = "해당 유저 번호를 가진 모든 상위 카테고리를 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/upper/list/user")
    public ResponseEntity<List<UpperCategoryVO>> findAllUpperCategory(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(upperCategoryService.findAll(userNo));
    }

    @Operation(summary = "모든 하위 카테고리 검색", description = "데이터 베이스 내부의 모든 하위 카테고리를 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/lower/list/user")
    public ResponseEntity<List<LowerCategoryVO>> findAllLowerCategory(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(lowerCategoryService.findAll(userNo));
    }

    @Operation(summary = "상위 카테고리 검색", description = "해당 상위 카테고리 번호를 가진 상위 카테고리를 검색하는 API")
    @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/upper")
    public ResponseEntity<UpperCategoryVO> findUpperCategory(
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return ResponseEntity.ok(upperCategoryService.find(upperCategoryNo));
    }

    @Operation(summary = "하위 카테고리 검색", description = "해당 하위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/lower")
    public ResponseEntity<LowerCategoryVO> findLowerCategory(
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return ResponseEntity.ok(lowerCategoryService.find(lowerCategoryNo));
    }

    @Operation(summary = "상위 카테고리로 하위 카테고리 검색", description = "해당 상위 카테고리 번호를 가진 하위 카테고리를 검색하는 API")
    @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/lower/list/upper")
    public ResponseEntity<List<LowerCategoryVO>> findLowerCategoryByUpper(
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return ResponseEntity.ok(lowerCategoryService.findByUpperCategory(upperCategoryNo));
    }

    @Operation(summary = "상위 카테고리 등록", description = "DTO를 받아 상위 카테고리를 등록하는 API")
    @PostMapping("/upper")
    public ResponseEntity<Long> saveUpperCategory(@RequestBody UpperCategorySaveDTO upperCategorySaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(upperCategoryService.save(upperCategorySaveDTO));
    }

    @Operation(summary = "하위 카테고리 등록", description = "DTO를 받아 하위 카테고리를 등록하는 API")
    @PostMapping("/lower")
    public ResponseEntity<Long> saveLowerCategory(@RequestBody LowerCategorySaveDTO lowerCategorySaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lowerCategoryService.save(lowerCategorySaveDTO));
    }

    @Operation(summary = "상위 카테고리 수정", description = "DTO를 받아 상위 카테고리를 수정하는 API")
    @PutMapping("/upper")
    public ResponseEntity<Long> updateUpperCategory(@RequestBody UpperCategoryUpdateDTO upperCategoryUpdateDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(upperCategoryService.update(upperCategoryUpdateDTO));
    }

    @Operation(summary = "하위 카테고리 수정", description = "DTO를 받아 하위 카테고리를 수정하는 API")
    @PutMapping("/lower")
    public ResponseEntity<Long> updateLowerCategory(@RequestBody LowerCategoryUpdateDTO lowerCategoryUpdateDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(lowerCategoryService.update(lowerCategoryUpdateDTO));
    }

    @Operation(summary = "상위 카테고리 삭제", description = "해당 상위 카테고리 번호를 가진 상위 카테고리를 삭제하는 API")
    @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @DeleteMapping("/upper")
    public ResponseEntity<Void> deleteUpperCategory(
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        upperCategoryService.delete(upperCategoryNo);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "하위 카테고리 삭제", description = "해당 하위 카테고리 번호를 가진 하위 카테고리를 삭제하는 API")
    @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @DeleteMapping("/lower")
    public ResponseEntity<Void> deleteLowerCategory(
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        lowerCategoryService.delete(lowerCategoryNo);

        return ResponseEntity.ok().build();
    }
}
