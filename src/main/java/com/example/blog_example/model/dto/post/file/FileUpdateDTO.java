package com.example.blog_example.model.dto.post.file;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Schema(description = "파일 수정 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
public class FileUpdateDTO {
    @ApiModelProperty(name = "postNo", value = "게시글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long postNo;

    @ApiModelProperty(name = "multipartFiles", value = "멀티 파트 파일 리스트", example = "example.jpg", required = true)
    @NotNull
    private List<MultipartFile> multipartFiles;
}
