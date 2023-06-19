package com.example.blog_example.model.dto.post.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@Getter
public class FileUpdateDTO {
    @Positive
    private Long postDetailNo;
    @NotNull
    private List<MultipartFile> multipartFiles;
}
