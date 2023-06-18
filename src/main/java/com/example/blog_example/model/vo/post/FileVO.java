package com.example.blog_example.model.vo.post;

import com.example.blog_example.model.domain.post.file.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileVO {
    private Long fileNo;
    private String name;
    private String originalFileName;
    private Long size;

    private FileVO(File file) {
        this.fileNo = file.getFileNo();
        this.name = file.getName();
        this.originalFileName = file.getOriginalFileName();
        this.size = file.getSize();
    }

    public static FileVO from(File file) { return new FileVO(file); }
}
