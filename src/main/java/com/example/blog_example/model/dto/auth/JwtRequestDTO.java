package com.example.blog_example.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class JwtRequestDTO {
    @Email
    private String email;
    @NotBlank
    private String password;
}
