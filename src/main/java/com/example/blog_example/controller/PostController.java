package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.dto.post.file.FileUpdateDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.post.PostDetailVO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostLikedService;
import com.example.blog_example.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Api(tags = {"게시글 API"})
@Validated
@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final PostLikedService postLikedService;
    private final FileService fileService;

    @ApiOperation(value = "모든 게시글 검색", notes = "데이터 베이스 내부의 모든 게시글을 검색하는 API")
    @GetMapping("/info/list")
    public List<PostVO> findAll() {
        return postService.findAll();
    }

    @ApiOperation(value = "게시글 검색", notes = "해당 게시글 번호를 가진 게시글을 검색하는 API")
    @GetMapping("/info")
    public PostDetailVO find(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        postService.addViews(postNo);

        return PostDetailVO.of(postService.find(postNo), fileService.findByPost(postNo));
    }

    @ApiOperation(value = "유저로 게시글 검색", notes = "해당 유저 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/info/user")
    public List<PostVO> findByUser(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return postService.findByUser(userNo);
    }

    @ApiOperation(value = "상위 카테고리로 게시글 검색", notes = "해당 상위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/info/upper")
    public List<PostVO> findByUpperCategory(
            @ApiParam(name = "upperCategoryNo", value = "상위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return postService.findByUpperCategory(upperCategoryNo);
    }

    @ApiOperation(value = "하위 카테고리로 게시글 검색", notes = "해당 하위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/info/lower")
    public List<PostVO> findByLowerCategory(
            @ApiParam(name = "lowerCategoryNo", value = "하위 카테고리 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return postService.findByLowerCategory(lowerCategoryNo);
    }

    @ApiOperation(value = "유저가 좋아요를 누른 게시글 검색", notes = "해당 유저가 좋아요를 누른 모든 게시글을 검색하는 API")
    @GetMapping("/info/liked/user")
    public List<PostVO> findPostLiked(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return postLikedService.findPostByUser(userNo);
    }

    @ApiOperation(value = "게시글 등록", notes = "DTO, MultipartFile을 받아 게시글을 등록하는 API")
    @PostMapping("/save")
    public Long save(
            @RequestBody PostSaveDTO postSaveDTO,
            @ApiParam(name = "multipartFiles", value = "업로드한 파일 리스트", example = "example.jpg")
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
        Long postNo = postService.save(postSaveDTO);

        if (!multipartFiles.isEmpty()) {
            FileSaveDTO fileSaveDTO = FileSaveDTO.builder()
                    .multipartFiles(multipartFiles)
                    .postNo(postNo)
                    .build();
            fileService.save(fileSaveDTO);
        }

        return postNo;
    }

    @ApiOperation(value = "게시글 수정", notes = "DTO, MultipartFile을 받아 게시글을 수정하는 API")
    @PutMapping("/update")
    public Long update(
            @RequestBody PostUpdateDTO postUpdateDTO,
            @ApiParam(name = "multipartFiles", value = "업로드한 파일 리스트", example = "example.jpg")
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
        if (!multipartFiles.isEmpty()) {
            FileUpdateDTO fileUpdateDTO = FileUpdateDTO.builder()
                    .postNo(postUpdateDTO.getPostNo())
                    .multipartFiles(multipartFiles)
                    .build();
            fileService.update(fileUpdateDTO);
        }
        return postService.update(postUpdateDTO);
    }

    @ApiOperation(value = "게시글 삭제", notes = "해당 게시글 번호의 게시글을 삭제하는 API")
    @DeleteMapping("/delete")
    public void delete(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        fileService.deleteByPost(postNo);
        postService.delete(postNo);
    }

    @ApiOperation(value = "게시글 좋아요 변경", notes = "해당 게시글 번호의 게시글의 좋아요 여부를 확인 후 변경하는 API")
    @GetMapping("/state/liked")
    public Boolean changeLiked(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam("post") @PositiveOrZero Long postNo,
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam("user") @PositiveOrZero Long userNo) {
        if (postService.isLiked(postNo)) {
            postLikedService.delete(postNo);

            return false;
        }

        PostLikedSaveDTO postLikedSaveDTO = PostLikedSaveDTO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();
        postLikedService.save(postLikedSaveDTO);

        return true;
    }

    @ApiOperation(value = "게시글 비공개 변경", notes = "해당 게시글 번호의 게시글의 비공개 여부를 확인 후 변경하는 API")
    @GetMapping("/state/open")
    public String changeOpenYN(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postService.changeOpenYN(postNo).toString();
    }

    @ApiOperation(value = "게시글 좋아요 확인", notes = "해당 게시글 번호의 게시글의 좋아요 여부를 확인하는 API")
    @GetMapping("/liked")
    public Boolean isLiked(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postService.isLiked(postNo);
    }

    @ApiOperation(value = "게시글 좋아요 갯수 출력", notes = "해당 게시글 번호의 게시글이 받은 좋아요의 수를 출력하는 API")
    @GetMapping("/liked/count")
    public Integer countLiked(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postLikedService.countByPost(postNo);
    }
}
