package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.file.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FileVO {
    @Positive
    private Long fileNo;

    @NotBlank
    private String name;

    @NotBlank
    private String originalFileName;

    @NotBlank
    private String path;

    @Positive
    private Long size;

    @NotNull
    private PostDetailVO postDetailVO;

    @PastOrPresent
    private LocalDateTime regDt;

    @PastOrPresent
    private LocalDateTime modDt;

    private FileVO(File file) {
        this.fileNo = file.getFileNo();
        this.name = file.getName();
        this.originalFileName = file.getOriginalFileName();
        this.path = file.getPath();
        this.size = file.getSize();
        this.postDetailVO = PostDetailVO.from(file.getPostDetail());
        this.regDt = file.getRegDt();
        this.modDt = file.getModDt();
    }

    public static FileVO from(File file) { return new FileVO(file); }
}
