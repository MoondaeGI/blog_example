package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.CommentUpdateDTO;
import com.example.blog_example.model.vo.enums.EnumStateVO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.service.comment.CommentService;
import com.example.blog_example.util.enums.LikedState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Objects;

@Tag(name = "comment", description = "댓글 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "모든 댓글 검색", description = "데이터 베이스 내부의 모든 댓글을 검색하는 API")
    @GetMapping("/list")
    public ResponseEntity<List<CommentVO>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @Operation(summary = "댓글 검색", description = "해당 댓글 번호를 가진 댓글을 검색하는 API")
    @Parameter(name = "commentNo", description = "댓글 번호", example = "1", required = true)
    @GetMapping
    public ResponseEntity<CommentVO> find(
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return ResponseEntity.ok(commentService.find(commentNo));
    }

    @Operation(summary = "게시글로 댓글 검색", description = "해당 게시글 번호를 가진 모든 댓글을 검색하는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/list/post")
    public ResponseEntity<List<CommentVO>> findByPost(
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return ResponseEntity.ok(commentService.findByPost(postNo));
    }

    @Operation(summary = "유저로 댓글 검색", description = "해당 유저 번호를 가진 모든 댓글을 검색하는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/list/user")
    public ResponseEntity<List<CommentVO>> findByUser(
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(commentService.findByUser(userNo));
    }

    @Operation(summary = "댓글 등록", description = "DTO를 가지고 댓글을 등록하는 API")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CommentSaveDTO commentSaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(commentSaveDTO));
    }

    @Operation(summary = "댓글 수정", description = "DTO를 가지고 댓글을 수정하는 API")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CommentUpdateDTO commentUpdateDTO) {
        return ResponseEntity.ok(commentService.update(commentUpdateDTO));
    }

    @Operation(summary = "댓글 삭제", description = "해당 댓글 번호를 가진 댓글을 삭제하는 API")
    @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        commentService.delete(commentNo);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 좋아요 변경", description = "해당 댓글 번호를 가진 댓글의 좋아요를 확인 후 변경하는 API")
    @Parameters({
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    })
    @GetMapping("/state/liked")
    public ResponseEntity<EnumStateVO> changeLiked(
            @RequestParam(name = "comment-no") @PositiveOrZero Long commentNo,
            @RequestParam(name = "user-no") @PositiveOrZero Long userNo) {
        LikedState state = commentService.changeLiked(commentNo, userNo);
        EnumStateVO result = EnumStateVO.builder()
                .enumState(state)
                .build();

        return Objects.equals(state, LikedState.LIKED) ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.ok(result);
    }

    @Operation(summary = "댓글 좋아요 확인", description = "해당 댓글 번호를 가진 댓글의 좋아요 여부를 확인하는 API")
    @Parameters({
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
    })
    @GetMapping("/liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam(name = "comment-no") @PositiveOrZero Long commentNo,
            @RequestParam(name = "user-no") @PositiveOrZero Long userNo) {
        return ResponseEntity.ok(commentService.isLiked(commentNo, userNo));
    }

    @Operation(summary = "댓글 좋아요 갯수 출력", description = "해당 댓글 번호를 가진 댓글의 좋아요 갯수를 출력하는 API")
    @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
    @GetMapping("/liked/count")
    public ResponseEntity<Integer> countLiked(
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return ResponseEntity.ok(commentService.countLiked(commentNo));
    }
}
