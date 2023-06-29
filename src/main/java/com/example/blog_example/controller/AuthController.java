package com.example.blog_example.controller;

import com.example.blog_example.model.dto.auth.JwtRequestDTO;
import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserSignupDTO userSignupDTO) {
        return authService.signup(userSignupDTO);
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody @Valid JwtRequestDTO jwtRequestDTO) {
        try {
            return authService.login(jwtRequestDTO);
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
