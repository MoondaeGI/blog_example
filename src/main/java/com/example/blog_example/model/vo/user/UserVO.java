package com.example.blog_example.model.vo.user;

import com.example.blog_example.model.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserVO {
    @Positive
    private Long userNo;

    @NotBlank
    private String name;

    @NotBlank
    private String blogName;

    @PastOrPresent
    private LocalDateTime regDt;

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
