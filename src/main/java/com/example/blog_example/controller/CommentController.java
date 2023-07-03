package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.comment.CommentUpdateDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.service.comment.CommentLikedService;
import com.example.blog_example.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Api(tags = {"댓글 API"})
@Validated
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentLikedService commentLikedService;

    @ApiOperation(value = "모든 댓글 검색", notes = "데이터 베이스 내부의 모든 댓글을 검색하는 API")
    @GetMapping("/info/list")
    public List<CommentVO> findAll() {
        return commentService.findAll();
    }

    @ApiOperation(value = "댓글 검색", notes = "해당 댓글 번호를 가진 댓글을 검색하는 API")
    @GetMapping("/info")
    public CommentVO find(
            @ApiParam(name = "commentNo", value = "댓글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentService.find(commentNo);
    }

    @ApiOperation(value = "게시글로 댓글 검색", notes = "해당 게시글 번호를 가진 모든 댓글을 검색하는 API")
    @GetMapping("/info/post")
    public List<CommentVO> findByPost(
            @ApiParam(name = "postNo", value = "게시글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return commentService.findByPost(postNo);
    }

    @ApiOperation(value = "유저로 댓글 검색", notes = "해당 유저 번호를 가진 모든 댓글을 검색하는 API")
    @GetMapping("/info/user")
    public List<CommentVO> findByUser(
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return commentService.findByUser(userNo);
    }

    @ApiOperation(value = "댓글 등록", notes = "DTO를 가지고 댓글을 등록하는 API")
    @PostMapping("/save")
    public Long save(@RequestBody CommentSaveDTO commentSaveDTO) {
        return commentService.save(commentSaveDTO);
    }

    @ApiOperation(value = "댓글 수정", notes = "DTO를 가지고 댓글을 수정하는 API")
    @PutMapping("/update")
    public Long update(@RequestBody CommentUpdateDTO commentUpdateDTO) {
        return commentService.update(commentUpdateDTO);
    }

    @ApiOperation(value = "댓글 삭제", notes = "해당 댓글 번호를 가진 댓글을 삭제하는 API")
    @DeleteMapping("/delete")
    public void delete(
            @ApiParam(name = "commentNo", value = "댓글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        commentService.delete(commentNo);
    }

    @ApiOperation(value = "댓글 좋아요 변경", notes = "해당 댓글 번호를 가진 댓글의 좋아요를 확인 후 변경하는 API")
    @GetMapping("/state/liked")
    public Boolean changeLiked(
            @ApiParam(name = "commentNo", value = "댓글 번호", example = "1", required = true)
            @RequestParam @PositiveOrZero Long commentNo,
            @ApiParam(name = "userNo", value = "유저 번호", example = "1", required = true)
            @RequestParam @PositiveOrZero Long userNo) {
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

    @ApiOperation(value = "댓글 좋아요 확인", notes = "해당 댓글 번호를 가진 댓글의 좋아요 여부를 확인하는 API")
    @GetMapping("/liked")
    public Boolean isLiked(
            @ApiParam(name = "commentNo", value = "댓글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentService.isLiked(commentNo);
    }

    @ApiOperation(value = "댓글 좋아요 갯수 출력", notes = "해당 댓글 번호를 가진 댓글의 좋아요 갯수를 출력하는 API")
    @GetMapping("/liked/count")
    public Integer countLiked(
            @ApiParam(name = "commentNo", value = "댓글 번호", example = "1", required = true)
            @RequestParam(name = "no") @PositiveOrZero Long commentNo) {
        return commentLikedService.countByComment(commentNo);
    }
}
