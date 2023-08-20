package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.file.File;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Schema(description = "파일 VO")
@NoArgsConstructor
@Getter
public class FileVO {
    @ApiModelProperty(name = "commentNo", value = "댓글 번호", example = "1", required = true)
    @PositiveOrZero
    private Long fileNo;

    @ApiModelProperty(name = "name", value = "파일 이름", example = "example", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(name = "originalFileName", value = "원본 파일 이름", example = "example.jpg", required = true)
    @NotBlank
    private String originalFileName;

    @ApiModelProperty(name = "path", value = "경로", example = "./example", required = true)
    @NotBlank
    private String path;

    @ApiModelProperty(name = "size", value = "크기", example = "1", required = true)
    @Positive
    private Long size;

    @ApiModelProperty(name = "regDt", value = "등록 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime regDt;

    @ApiModelProperty(name = "modDt", value = "수정 날짜", example = "2020-01-01T00:00:00", required = true)
    @PastOrPresent
    private LocalDateTime modDt;

    private FileVO(File file) {
        this.fileNo = file.getFileNo();
        this.name = file.getName();
        this.originalFileName = file.getOriginalFileName();
        this.path = file.getPath();
        this.size = file.getSize();
        this.regDt = file.getRegDt();
        this.modDt = file.getModDt();
    }

    public static FileVO from(File file) { return new FileVO(file); }
}
