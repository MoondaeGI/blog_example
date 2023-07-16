package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.dto.post.file.FileUpdateDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.enums.EnumStateVO;
import com.example.blog_example.model.vo.post.FileVO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostService;
import com.example.blog_example.util.enums.state.LikedState;
import com.example.blog_example.util.enums.state.OpenState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "post", description = "게시글 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    @Operation(summary = "모든 게시글 검색", description = "데이터 베이스 내부의 모든 게시글을 검색하는 API")
    @ApiResponse(responseCode = "200", description = "정상 작동되었습니다.")
    @GetMapping("/list")
    public List<PostVO> findAll() {
        return postService.findAll();
    }

    @Operation(summary = "게시글 검색", description = "해당 게시글 번호를 가진 게시글을 검색하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @GetMapping
    public ResponseEntity<PostVO> find(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(postService.find(postNo));
    }

    @Operation(summary = "파일 리스트 검색", description = "해당 게시글 번호를 가진 파일 리스트를 검색하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @GetMapping("/file/list/post")
    public ResponseEntity<List<FileVO>> findFileList(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return fileService.isExist(postNo) ? ResponseEntity.ok(fileService.findByPost(postNo)) : null;
    }

    @Operation(summary = "유저로 게시글 검색", description = "해당 유저 번호를 가진 모든 게시글을 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/list/user")
    public ResponseEntity<List<PostVO>> findByUser(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(postService.findByUser(userNo));
    }

    @Operation(summary = "상위 카테고리로 게시글 검색", description = "해당 상위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @Parameter(name = "upperCategoryNo", description = "상위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 상위 카테고리가 없습니다.")
    })
    @GetMapping("/list/upper-category")
    public ResponseEntity<List<PostVO>> findByUpperCategory(
            @RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return ResponseEntity.ok(postService.findByUpperCategory(upperCategoryNo));
    }

    @Operation(summary = "하위 카테고리로 게시글 검색", description = "해당 하위 카테고리 번호를 가진 모든 게시글을 검색하는 API")
    @Parameter(name = "lowerCategoryNo", description = "하위 카테고리 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 하위 카테고리가 없습니다.")
    })
    @GetMapping("/list/lower-category")
    public ResponseEntity<List<PostVO>> findByLowerCategory(
            @RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return ResponseEntity.ok(postService.findByLowerCategory(lowerCategoryNo));
    }

    @Operation(summary = "유저가 좋아요를 누른 모든 게시글 검색", description = "해당 유저가 좋아요를 누른 모든 게시글을 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/liked/list/user")
    public ResponseEntity<List<PostVO>> findPostLikedList(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(postService.findPostLikedList(userNo));
    }

    @Operation(summary = "게시글 등록", description = "DTO, MultipartFile을 받아 게시글을 등록하는 API")
    @Parameters({
            @Parameter(name = "postSaveDTO", description = "게시글 등록 요청 DTO", required = true),
            @Parameter(name = "multipartFiles", description = "업로드한 파일 리스트")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글이 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 상위 카테고리가 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 하위 카테고리가 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @PostMapping
    public ResponseEntity<Long> save(
            @RequestPart(value = "dto") PostSaveDTO postSaveDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long postNo = postService.save(postSaveDTO);

        if (multipartFiles != null) {
            FileSaveDTO fileSaveDTO = FileSaveDTO.builder()
                    .multipartFiles(multipartFiles)
                    .postNo(postNo)
                    .build();
            fileService.save(fileSaveDTO);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postNo);
    }

    @Operation(summary = "게시글 수정", description = "DTO, MultipartFile을 받아 게시글을 수정하는 API")
    @Parameters({
            @Parameter(name = "postUpdateDTO", description = "게시글 수정 요청 DTO", required = true),
            @Parameter(name = "multipartFiles", description = "업로드한 파일 리스트")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글이 수정되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 상위 카테고리가 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 하위 카테고리가 없습니다.")
    })
    @PostMapping("/update")
    public ResponseEntity<Long> update(
            @RequestPart(value = "dto") PostUpdateDTO postUpdateDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long postNo = postService.update(postUpdateDTO);

        List<MultipartFile> updateFileList = (multipartFiles != null) ? multipartFiles : new ArrayList<>();

        FileUpdateDTO fileUpdateDTO = FileUpdateDTO.builder()
                .postNo(postUpdateDTO.getPostNo())
                .multipartFiles(updateFileList)
                .build();
        fileService.update(fileUpdateDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postNo);
    }

    @Operation(summary = "게시글 삭제", description = "해당 게시글 번호의 게시글을 삭제하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "해당 번호를 가진 게시글이 삭제되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        fileService.deleteByPost(postNo);
        postService.delete(postNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "게시글 좋아요 변경", description = "해당 게시글 번호의 게시글의 좋아요 여부를 확인 후 변경하는 API")
    @Parameters({
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "좋아요 하려는 유저는 이 게시글을 쓴 유저입니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/state/liked")
    public ResponseEntity<EnumStateVO> changeLiked(
            @RequestParam("post-no") @PositiveOrZero Long postNo,
            @RequestParam("user-no") @PositiveOrZero Long userNo) {
        LikedState state = postService.changeLiked(postNo, userNo);
        EnumStateVO result = EnumStateVO.builder()
                .enumState(state)
                .build();

        return Objects.equals(state, LikedState.LIKED) ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.ok(result);
    }

    @Operation(summary = "게시글 비공개 변경", description = "해당 게시글 번호의 게시글의 비공개 여부를 확인 후 변경하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @GetMapping("/state/open")
    public ResponseEntity<EnumStateVO> changeOpenYN(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        OpenState state = postService.changeOpenYN(postNo);
        EnumStateVO result = EnumStateVO.builder()
                .enumState(state)
                .build();

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "게시글 좋아요 확인", description = "해당 게시글 번호의 게시글의 좋아요 여부를 확인하는 API")
    @Parameters({
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저는 이 게시글을 쓴 유저입니다"),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 유저가 없습니다.")
    })
    @GetMapping("/liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam(name = "post-no") @PositiveOrZero Long postNo,
            @RequestParam(name = "user-no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(postService.isLiked(postNo, userNo));
    }

    @Operation(summary = "게시글 좋아요 갯수 출력", description = "해당 게시글 번호의 게시글이 받은 좋아요의 수를 출력하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 번호를 가진 게시글이 없습니다.")
    })
    @GetMapping("/liked/count")
    public ResponseEntity<Integer> countLiked(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(postService.countLiked(postNo));
    }
}
