package com.example.blog_example.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Schema(description = "로그인 요청 DTO")
@AllArgsConstructor
@Getter
public class JwtRequestDTO {
    @ApiModelProperty(name = "email", value = "이메일", example = "ex1234@example.com", required = true)
    @Email
    private String email;

    @ApiModelProperty(name = "password", value = "비밀번호", example = "example1234@", required = true)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")
    private String password;
}
