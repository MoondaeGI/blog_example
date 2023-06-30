package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.post.PostDetailVO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostLikedService;
import com.example.blog_example.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    private final PostLikedService postLikedService;
    private final FileService fileService;

    @GetMapping("/info/list")
    public List<PostVO> postFindAll() {
        return postService.findAll();
    }

    @GetMapping("/info")
    public PostDetailVO postFind(@RequestParam(name = "no") Long postNo) {
        postService.addViews(postNo);

        return PostDetailVO.of(postService.find(postNo), fileService.findByPost(postNo));
    }

    @GetMapping("/info/user")
    public List<PostVO> postFindByUser(@RequestParam(name = "no") Long userNo) {
        return postService.findByUser(userNo);
    }

    @GetMapping("/info/upper")
    public List<PostVO> postFindByUpperCategory(@RequestParam(name = "no") Long upperCategoryNo) {
        return postService.findByUpperCategory(upperCategoryNo);
    }

    @GetMapping("/info/lower")
    public List<PostVO> postFindByLowerCategory(@RequestParam(name = "no") Long lowerCategoryNo) {
        return postService.findByLowerCategory(lowerCategoryNo);
    }

    @PostMapping("/save")
    public Long postSave(@RequestBody @Valid PostSaveDTO postSaveDTO) {
        return postService.save(postSaveDTO);
    }

    @PostMapping("/push/liked")
    public Long pushLiked(@RequestBody @Valid PostLikedSaveDTO postLikedSaveDTO) {
        if (postService.isLiked(postLikedSaveDTO.getPostNo())) {
            postLikedService.delete(postLikedSaveDTO.getPostNo());

            return postLikedSaveDTO.getPostNo();
        }

        return postLikedService.save(postLikedSaveDTO);
    }

    @PutMapping("/update")
    public Long postUpdate(@RequestBody @Valid PostUpdateDTO postUpdateDTO) {
        return postService.update(postUpdateDTO);
    }

    @DeleteMapping("/delete")
    public void postDelete(@RequestParam(name = "no") Long postNo) {
        postService.delete(postNo);
    }

    @GetMapping("/state")
    public String changeOpenYN(@RequestParam(name = "no") Long postNo) {
        return postService.changeOpenYN(postNo).toString();
    }

    @GetMapping("/is/liked")
    public Boolean isLiked(@RequestParam(name = "no") Long postNo) {
        return postService.isLiked(postNo);
    }
}
