package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.dto.post.file.FileUpdateDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.post.FileVO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostLikedService;
import com.example.blog_example.service.post.PostService;
import com.example.blog_example.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Objects;

@Tag(name = "post", description = "게시글 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final PostLikedService postLikedService;
    private final FileService fileService;
    private final UserService userService;

    @Operation(summary = "모든 게시글 검색", description = "데이터 베이스 내부의 모든 게시글을 검색하는 API")
    @GetMapping("/list")
    public List<PostVO> findAll() {
        return postService.findAll();
    }

    @Operation(summary = "게시글 검색", description = "해당 게시글 번호를 가진 게시글을 검색하는 API")
    @GetMapping
    public ResponseEntity<PostVO> find(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        postService.addViews(postNo);

        return ResponseEntity.ok(postService.find(postNo));
    }

    @Operation(summary = "파일 리스트 검색", description = "해당 게시글 번호를 가진 파일 리스트를 검색하는 API")
    @GetMapping("/file/list/post")
    public ResponseEntity<List<FileVO>> findFileList(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return fileService.isExist(postNo) ? ResponseEntity.ok(fileService.findByPost(postNo)) : null;
    }

    @Operation(summary = "유저로 게시글 검색", description = "해당 유저 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/list/user")
    public ResponseEntity<List<PostVO>> findByUser(
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(postService.findByUser(userNo));
    }

    @Operation(summary = "상위 카테고리로 게시글 검색", description = "해당 상위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/list/upper-category")
    public ResponseEntity<List<PostVO>> findByUpperCategory(
            @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return ResponseEntity.ok(postService.findByUpperCategory(upperCategoryNo));
    }

    @Operation(summary = "하위 카테고리로 게시글 검색", description = "해당 하위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @GetMapping("/list/lower-category")
    public ResponseEntity<List<PostVO>> findByLowerCategory(
            @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return ResponseEntity.ok(postService.findByLowerCategory(lowerCategoryNo));
    }

    @Operation(summary = "유저가 좋아요를 누른 모든 게시글 검색", description = "해당 유저가 좋아요를 누른 모든 게시글을 검색하는 API")
    @GetMapping("/liked/list/user")
    public ResponseEntity<List<PostVO>> findPostLikedList(
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(postLikedService.findPostByUser(userNo));
    }

    @Operation(summary = "게시글 등록", description = "DTO, MultipartFile을 받아 게시글을 등록하는 API")
    @PostMapping
    public ResponseEntity<Long> save(
            @RequestPart PostSaveDTO postSaveDTO,
            @Parameter(name = "multipartFiles", description = "업로드한 파일 리스트")
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
        Long postNo = postService.save(postSaveDTO);

        if (!multipartFiles.isEmpty()) {
            FileSaveDTO fileSaveDTO = FileSaveDTO.builder()
                    .multipartFiles(multipartFiles)
                    .postNo(postNo)
                    .build();
            fileService.save(fileSaveDTO);
        }

        return ResponseEntity.ok(postNo);
    }

    @Operation(summary = "게시글 수정", description = "DTO, MultipartFile을 받아 게시글을 수정하는 API")
    @PutMapping
    public ResponseEntity<Long> update(
            @RequestPart PostUpdateDTO postUpdateDTO,
            @Parameter(name = "multipartFiles", description = "업로드한 파일 리스트")
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
        if (!multipartFiles.isEmpty()) {
            FileUpdateDTO fileUpdateDTO = FileUpdateDTO.builder()
                    .postNo(postUpdateDTO.getPostNo())
                    .multipartFiles(multipartFiles)
                    .build();
            fileService.update(fileUpdateDTO);
        }
        return ResponseEntity.ok(postService.update(postUpdateDTO));
    }

    @Operation(summary = "게시글 삭제", description = "해당 게시글 번호의 게시글을 삭제하는 API")
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        fileService.deleteByPost(postNo);
        postService.delete(postNo);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 좋아요 변경", description = "해당 게시글 번호의 게시글의 좋아요 여부를 확인 후 변경하는 API")
    @GetMapping("/state/liked")
    public ResponseEntity<Boolean> changeLiked(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam("post-no") @PositiveOrZero Long postNo,
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam("user-no") @PositiveOrZero Long userNo) {
        if (Objects.equals(userService.findByPost(postNo).getUserNo(), userNo)) throw new IllegalArgumentException();

        if (postService.isLiked(postNo)) {
            postLikedService.delete(postNo);
        } else {
            PostLikedSaveDTO postLikedSaveDTO = PostLikedSaveDTO.builder()
                    .postNo(postNo)
                    .userNo(userNo)
                    .build();
            postLikedService.save(postLikedSaveDTO);
        }

        return ResponseEntity.ok(postService.isLiked(postNo));
    }

    @Operation(summary = "게시글 비공개 변경", description = "해당 게시글 번호의 게시글의 비공개 여부를 확인 후 변경하는 API")
    @GetMapping("/state/open")
    public ResponseEntity<String> changeOpenYN(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "post-no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(postService.changeOpenYN(postNo).toString());
    }

    @Operation(summary = "게시글 좋아요 확인", description = "해당 게시글 번호의 게시글의 좋아요 여부를 확인하는 API")
    @GetMapping("/liked")
    public ResponseEntity<Boolean> isLiked(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(postService.isLiked(postNo));
    }

    @Operation(summary = "게시글 좋아요 갯수 출력", description = "해당 게시글 번호의 게시글이 받은 좋아요의 수를 출력하는 API")
    @GetMapping("/liked/count")
    public ResponseEntity<Integer> countLiked(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(postLikedService.countByPost(postNo));
    }
}
