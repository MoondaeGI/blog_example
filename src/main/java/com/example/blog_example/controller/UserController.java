package com.example.blog_example.controller;

import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.model.dto.user.user.UserInfoUpdateDTO;
import com.example.blog_example.model.vo.user.UserVO;
import com.example.blog_example.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.*;

@Tag(name = "user", description = "유저 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 등록", description = "DTO를 받아 유저를 생성하는 API")
    @ApiResponse(responseCode = "201", description = "유저 정보가 생성되었습니다.")
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@ApiIgnore @RequestBody UserSignupDTO userSignupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userSignupDTO));
    }

    @Operation(summary = "유저 검색", description = "해당 번호를 가진 유저를 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping
    public ResponseEntity<UserVO> find(@RequestParam("no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(userService.find(userNo));
    }

    @Operation(summary = "이메일 중복 사용 확인", description = "해당 이메일을 가진 유저가 존재하는지 확인하는 API")
    @Parameter(name = "email", description = "이메일", example = "example1234@example.com", required = true)
    @ApiResponse(responseCode = "200", description = "정상 작동되었습니다.")
    @GetMapping("/email")
    public ResponseEntity<Boolean> isExistByEmail(@RequestParam("email") @Email String email) {
        return ResponseEntity.ok(userService.isExistByEmail(email));
    }

    @Operation(summary = "이름 중복 사용 확인", description = "해당 이름을 가진 유저가 존재하는지 확인하는 API")
    @Parameter(name = "name", description = "이름", example = "example", required = true)
    @ApiResponse(responseCode = "200", description = "정상 작동되었습니다.")
    @GetMapping("/name")
    public ResponseEntity<Boolean> isExistByName(@RequestParam("name") @NotBlank @Size(max = 10) String name) {
        return ResponseEntity.ok(userService.isExistByName(name));
    }

    @Operation(summary = "블로그 이름 중복 사용 확인", description = "해당 블로그 이름을 가진 유저가 존재하는지 확인하는 API")
    @Parameter(name = "blogName", description = "블로그 이름", example = "example", required = true)
    @ApiResponse(responseCode = "200", description = "정상 작동되었습니다.")
    @GetMapping("/blog")
    public ResponseEntity<Boolean> isExistByBlogName(@RequestParam("name") @NotBlank @Size(max = 20) String blogName) {
        return ResponseEntity.ok(userService.isExistByBlogName(blogName));
    }

    @Operation(summary = "유저 정보 수정", description = "해당 번호를 가진 유저의 정보를 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @PutMapping()
    public ResponseEntity<Long> update(UserInfoUpdateDTO userInfoUpdateDTO) {
        return ResponseEntity.ok(userService.update(userInfoUpdateDTO));
    }
}
