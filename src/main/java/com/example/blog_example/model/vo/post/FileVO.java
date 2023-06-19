package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.file.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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

    private FileVO(File file) {
        this.fileNo = file.getFileNo();
        this.name = file.getName();
        this.originalFileName = file.getOriginalFileName();
        this.path = file.getPath();
        this.size = file.getSize();
    }

    public static FileVO from(File file) { return new FileVO(file); }
}
