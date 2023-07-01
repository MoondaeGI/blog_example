package com.example.blog_example.model.vo.user;

import com.example.blog_example.model.domain.user.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserVO {
    @ApiModelProperty(name = "userNo", value = "유저 번호", example = "1", required = true)
    @Positive
    private Long userNo;

    @ApiModelProperty(name = "name", value = "유저 이름", example = "example", required = true)
    @NotBlank @Max(10)
    private String name;

    @ApiModelProperty(name = "blogName", value = "블로그 이름", example = "example", required = true)
    @NotBlank @Max(20)
    private String blogName;

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
        this.regDt = user.getRegDt();
        this.modDt = user.getModDt();
    }

    public static UserVO from(User user) { return new UserVO(user); }
}
