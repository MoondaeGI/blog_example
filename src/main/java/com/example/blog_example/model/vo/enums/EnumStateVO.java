package com.example.blog_example.model.vo.enums;

import com.example.blog_example.util.enums.state.EnumState;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Enum 타입 VO")
@NoArgsConstructor
@Getter
public class EnumStateVO {
    @ApiModelProperty(name = "state", value = "상태값", example = "LIKED", required = true)
    private String state;
    @ApiModelProperty(name = "description", value = "상태 설명", example = "좋아요", required = true)
    private String description;

    @Builder
    public EnumStateVO(EnumState enumState) {
        this.state = enumState.getState();
        this.description = enumState.getDescription();
    }
}
