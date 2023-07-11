package com.example.blog_example.controller;

import com.example.blog_example.model.dto.auth.JwtRequestDTO;
import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.service.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "auth", description = "인증 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원 등록", description = "DTO를 받아 유저를 생성하는 API")
    @ApiResponse(responseCode = "201", description = "유저 정보가 생성되었습니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@ApiIgnore @RequestBody UserSignupDTO userSignupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signup(userSignupDTO));
    }

    @Operation(summary = "로그인", description = "DTO를 받아 유저의 로그인을 실행하는 API")
    @ApiResponse(responseCode = "200", description = "로그인 정상 작동되었습니다.")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@ApiIgnore @RequestBody JwtRequestDTO jwtRequestDTO) {
        return ResponseEntity.ok(authService.login(jwtRequestDTO));
    }
}
