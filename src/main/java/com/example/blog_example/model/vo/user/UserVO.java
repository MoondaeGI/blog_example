package com.example.blog_example.model.vo.user;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.util.enums.Role;
import com.example.blog_example.util.annotation.valid.Enum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "유저 VO")
@NoArgsConstructor
@Getter
public class UserVO implements Serializable {
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @Positive
    private Long userNo;

    @ApiModelProperty(name = "name", value = "유저 이름", example = "example", required = true)
    @NotBlank @Size(max = 10)
    private String name;

    @ApiModelProperty(name = "blogName", value = "블로그 이름", example = "example", required = true)
    @NotBlank @Size(max = 20)
    private String blogName;

    @ApiModelProperty(name = "role", value = "접근 권한", example = "USER", required = true)
    @Enum(enumClass = Role.class, ignoreCase = true)
    private Role role;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime modDt;

    private UserVO(User user) {
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.blogName = user.getBlogName();
        this.role = user.getRole();
        this.regDt = user.getRegDt();
        this.modDt = user.getModDt();
    }

    public static UserVO from(User user) { return new UserVO(user); }
}
