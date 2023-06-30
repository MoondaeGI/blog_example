package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.comment.CommentUpdateDTO;
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

    @GetMapping("/info")
    public CommentVO find(@RequestParam(name = "no") Long commentNo) {
        return commentService.find(commentNo);
    }

    @GetMapping("/info/post")
    public List<CommentVO> findByPost(@RequestParam(name = "no") Long postNo) {
        return commentService.findByPost(postNo);
    }

    @GetMapping("/info/user")
    public List<CommentVO> findByUser(@RequestParam(name = "no") Long userNo) {
        return commentService.findByUser(userNo);
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
        commentService.delete(commentNo);
    }

    @DeleteMapping("/liked/delete")
    public void commentLikedDelete(@RequestParam(name = "no") Long commentNo) {
        commentLikedService.delete(commentNo);
    }

    @GetMapping("/liked/count")
    public Integer countLiked(@RequestParam(name = "no") Long commentNo) {
        return commentLikedService.countByComment(commentNo);
    }

    @GetMapping("/info/liked")
    public Boolean isLiked(@RequestParam(name = "no") Long commentNo) {
        return commentService.isLiked(commentNo);
    }
}
