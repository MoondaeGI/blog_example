package com.example.blog_example.model.dto.user.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class UserSignupDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String blogName;
    @Email
    private String email;
    @NotBlank
    private String password;
}
