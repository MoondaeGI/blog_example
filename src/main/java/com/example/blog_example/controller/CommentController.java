package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.comment.CommentUpdateDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.service.comment.CommentLikedService;
import com.example.blog_example.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "comment", description = "댓글 API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentLikedService commentLikedService;

    @Operation(summary = "모든 댓글 검색", description = "데이터 베이스 내부의 모든 댓글을 검색하는 API")
    @GetMapping("/list")
    public List<CommentVO> findAll() {
        return commentService.findAll();
    }

    @Operation(summary = "댓글 검색", description = "해당 댓글 번호를 가진 댓글을 검색하는 API")
    @GetMapping
    public CommentVO find(
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentService.find(commentNo);
    }

    @Operation(summary = "게시글로 댓글 검색", description = "해당 게시글 번호를 가진 모든 댓글을 검색하는 API")
    @GetMapping("/post")
    public List<CommentVO> findByPost(
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return commentService.findByPost(postNo);
    }

    @Operation(summary = "유저로 댓글 검색", description = "해당 유저 번호를 가진 모든 댓글을 검색하는 API")
    @GetMapping("/user")
    public List<CommentVO> findByUser(
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return commentService.findByUser(userNo);
    }

    @ApiOperation(value = "댓글 등록", notes = "DTO를 가지고 댓글을 등록하는 API")
    @PostMapping
    public Long save(@RequestBody CommentSaveDTO commentSaveDTO) {
        return commentService.save(commentSaveDTO);
    }

    @Operation(summary = "댓글 수정", description = "DTO를 가지고 댓글을 수정하는 API")
    @PutMapping
    public Long update(@RequestBody CommentUpdateDTO commentUpdateDTO) {
        return commentService.update(commentUpdateDTO);
    }

    @Operation(summary = "댓글 삭제", description = "해당 댓글 번호를 가진 댓글을 삭제하는 API")
    @DeleteMapping
    public void delete(
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        commentService.delete(commentNo);
    }

    @Operation(summary = "댓글 좋아요 변경", description = "해당 댓글 번호를 가진 댓글의 좋아요를 확인 후 변경하는 API")
    @GetMapping("/state/liked")
    public Boolean changeLiked(
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "comment-no") @PositiveOrZero Long commentNo,
            @Parameter(name = "userNo", description = "유저 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "user-no") @PositiveOrZero Long userNo) {
        if (commentService.isLiked(commentNo)) {
            commentLikedService.delete(commentNo);

            return false;
        }

        CommentLikedSaveDTO commentLikedSaveDTO = CommentLikedSaveDTO.builder()
                .commentNo(commentNo)
                .userNo(userNo)
                .build();
        commentLikedService.save(commentLikedSaveDTO);

        return true;
    }

    @Operation(summary = "댓글 좋아요 확인", description = "해당 댓글 번호를 가진 댓글의 좋아요 여부를 확인하는 API")
    @GetMapping("/liked")
    public Boolean isLiked(
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentService.isLiked(commentNo);
    }

    @Operation(summary = "댓글 좋아요 갯수 출력", description = "해당 댓글 번호를 가진 댓글의 좋아요 갯수를 출력하는 API")
    @GetMapping("/liked/count")
    public Integer countLiked(
            @Parameter(name = "commentNo", description = "댓글 번호", example = "1", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentLikedService.countByComment(commentNo);
    }
}
