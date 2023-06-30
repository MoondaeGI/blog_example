package com.example.blog_example.controller;

import com.example.blog_example.model.dto.auth.JwtRequestDTO;
import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.service.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = {"인증 API"})
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "회원 등록", notes = "DTO를 받아 유저를 생성하는 API")
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupDTO userSignupDTO) {
        return authService.signup(userSignupDTO);
    }

    @ApiOperation(value = "로그인", notes = "DTO를 받아 유저의 로그인을 실행하는 API")
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody JwtRequestDTO jwtRequestDTO) {
        try {
            return authService.login(jwtRequestDTO);
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
