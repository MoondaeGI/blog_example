package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.comment.*;
import com.example.blog_example.model.dto.comment.liked.CommentLikedCountDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedDeleteDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.service.comment.CommentLikedService;
import com.example.blog_example.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {
    private final CommentService commentService;
    private final CommentLikedService commentLikedService;

    @GetMapping("/info/list")
    public List<CommentVO> findAll() {
        return commentService.findAll();
    }

    @GetMapping("/info/post")
    public List<CommentVO> findByPost(@RequestParam(name = "no") Long postNo) {
        CommentFindByObjectDTO commentFindByPostDTO = CommentFindByObjectDTO.builder()
                .objectNo(postNo)
                .build();

        return commentService.findByPost(commentFindByPostDTO);
    }

    @GetMapping("/info/user")
    public List<CommentVO> findByUser(@RequestParam(name = "no") Long userNo) {
        CommentFindByObjectDTO commentFindByUserDTO = CommentFindByObjectDTO.builder()
                .objectNo(userNo)
                .build();

        return commentService.findByUser(commentFindByUserDTO);
    }

    @PostMapping("/save")
    public Long commentSave(@RequestBody @Valid CommentSaveDTO commentSaveDTO) {
        return commentService.save(commentSaveDTO);
    }

    @PostMapping("/liked/save")
    public Long commentLikedSave(@RequestBody @Valid CommentLikedSaveDTO commentLikedSaveDTO) {
        return commentLikedService.save(commentLikedSaveDTO);
    }

    @PutMapping("/update")
    public Long commentUpdate(@RequestBody @Valid CommentUpdateDTO commentUpdateDTO) {
        return commentService.update(commentUpdateDTO);
    }

    @DeleteMapping("/delete")
    public void commentDelete(@RequestParam(name = "no") Long commentNo) {
        CommentDeleteDTO commentDeleteDTO = CommentDeleteDTO.builder()
                .commentNo(commentNo)
                .build();

        commentService.delete(commentDeleteDTO);
    }

    @DeleteMapping("/liked/delete")
    public void commentLikedDelete(@RequestParam(name = "no") Long commentNo) {
        CommentLikedDeleteDTO commentLikedDeleteDTO = CommentLikedDeleteDTO.builder()
                .commentNo(commentNo)
                .build();

        commentLikedService.delete(commentLikedDeleteDTO);
    }

    @GetMapping("/liked/count")
    public Integer countLiked(@RequestParam(name = "no") Long commentNo) {
        CommentLikedCountDTO commentLikedCountDTO = CommentLikedCountDTO.builder()
                .commentNo(commentNo)
                .build();

        return commentLikedService.countByComment(commentLikedCountDTO);
    }

    @GetMapping("/info/liked")
    public Boolean isLiked(@RequestParam(name = "no") Long commentNo) {
        CommentIsLikedDTO commentIsLikedDTO = CommentIsLikedDTO.builder()
                .commentNo(commentNo)
                .build();

        return commentService.isLiked(commentIsLikedDTO);
    }
}
