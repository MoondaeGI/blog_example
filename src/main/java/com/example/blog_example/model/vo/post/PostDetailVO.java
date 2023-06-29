package com.example.blog_example.model.vo.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostDetailVO {
    @NotNull
    private PostVO postVO;
    @NotNull
    private List<FileVO> fileVOList;

    private PostDetailVO(PostVO postVO, List<FileVO> fileVOList) {
        this.postVO = postVO;
        this.fileVOList = fileVOList;
    }

    public static PostDetailVO of(PostVO postVO, List<FileVO> fileVOList) {
        return new PostDetailVO(postVO, fileVOList);
    }
}
