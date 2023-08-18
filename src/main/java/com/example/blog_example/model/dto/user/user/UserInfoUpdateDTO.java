package com.example.blog_example.model.dto.user.user;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Schema(description = "유저 정보 수정 DTO")
@Builder
@AllArgsConstructor
@Getter
public class UserInfoUpdateDTO {
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @PositiveOrZero
    private Long userNo;

    @ApiModelProperty(name = "name", value = "이름", example = "example", required = true)
    @NotNull @Size(max = 10)
    private String name;

    @ApiModelProperty(name = "blogName", value = "블로그 이름", example = "example", required = true)
    @NotNull @Size(max = 20)
    private String blogName;
}
