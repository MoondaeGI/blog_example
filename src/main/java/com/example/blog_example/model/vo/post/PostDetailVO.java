package com.example.blog_example.model.vo.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "게시글 상세 VO")
@NoArgsConstructor
@Getter
public class PostDetailVO {
    @ApiModelProperty(name = "postVO", value = "게시글 VO", required = true)
    @NotNull
    private PostVO postVO;

    @ApiModelProperty(name = "fileVOList", value = "파일 VO 리스트", required = true)
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
