package com.example.blog_example.controller;

import com.example.blog_example.model.dto.comment.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.comment.CommentUpdateDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.service.comment.CommentLikedService;
import com.example.blog_example.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
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
    public Long save(@RequestBody CommentSaveDTO commentSaveDTO) {
        return commentService.save(commentSaveDTO);
    }

    @GetMapping("/state/liked")
    public Boolean changeLiked(
            @RequestParam @PositiveOrZero Long commentNo, @RequestParam @PositiveOrZero Long userNo) {
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

    @PutMapping("/update")
    public Long update(@RequestBody CommentUpdateDTO commentUpdateDTO) {
        return commentService.update(commentUpdateDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "no") Long commentNo) {
        commentService.delete(commentNo);
    }

    @GetMapping("/liked/count")
    public Integer countLiked(@RequestParam(name = "no") Long commentNo) {
        return commentLikedService.countByComment(commentNo);
    }

    @GetMapping("/liked")
    public Boolean isLiked(@RequestParam(name = "no") Long commentNo) {
        return commentService.isLiked(commentNo);
    }
}
