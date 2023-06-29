package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.file.FileFindAllDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedDeleteDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.dto.post.post.*;
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
        PostFindDTO postFindDTO = PostFindDTO.builder()
                .postNo(postNo)
                .build();

        FileFindAllDTO fileFindAllDTO = FileFindAllDTO.builder()
                .postNo(postNo)
                .build();

        PostAddViewsDTO postAddViewsDTO = PostAddViewsDTO.builder()
                .postNo(postNo)
                .build();
        postService.addViews(postAddViewsDTO);

        return PostDetailVO.of(postService.find(postFindDTO), fileService.findAll(fileFindAllDTO));
    }

    @GetMapping("/info/user")
    public List<PostVO> postFindByUser(@RequestParam(name = "no") Long userNo) {
        PostFindByObjectDTO postFindByUserDTO = PostFindByObjectDTO.builder()
                .objectNo(userNo)
                .build();

        return postService.findByUser(postFindByUserDTO);
    }

    @GetMapping("/info/upper")
    public List<PostVO> postFindByUpperCategory(@RequestParam(name = "no") Long upperCategoryNo) {
        PostFindByObjectDTO postFindByUpperCategory = PostFindByObjectDTO.builder()
                .objectNo(upperCategoryNo)
                .build();

        return postService.findByUpperCategory(postFindByUpperCategory);
    }

    @GetMapping("/info/lower")
    public List<PostVO> postFindByLowerCategory(@RequestParam(name = "no") Long lowerCategoryNo) {
        PostFindByObjectDTO postFindByLowerCategory = PostFindByObjectDTO.builder()
                .objectNo(lowerCategoryNo)
                .build();

        return postService.findByLowerCategory(postFindByLowerCategory);
    }

    @PostMapping("/save")
    public Long postSave(@RequestBody @Valid PostSaveDTO postSaveDTO) {
        return postService.save(postSaveDTO);
    }

    @PostMapping("/push/liked")
    public Long pushLiked(@RequestBody @Valid PostLikedSaveDTO postLikedSaveDTO) {
        PostIsLikedDTO postIsLikedDTO = PostIsLikedDTO.builder()
                .postNo(postLikedSaveDTO.getPostNo())
                .build();

        if (postService.isLiked(postIsLikedDTO)) {
            PostLikedDeleteDTO postLikedDeleteDTO = new PostLikedDeleteDTO(postLikedSaveDTO.getPostNo());
            postLikedService.delete(postLikedDeleteDTO);

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
        PostDeleteDTO postDeleteDTO = PostDeleteDTO.builder()
                .postNo(postNo)
                .build();

        postService.delete(postDeleteDTO);
    }

    @GetMapping("/state")
    public String changeOpenYN(@RequestParam(name = "no") Long postNo) {
        PostChangeOpenYNDTO postChangeOpenYNDTO = PostChangeOpenYNDTO.builder()
                .postNo(postNo)
                .build();

        return postService.changeOpenYN(postChangeOpenYNDTO).toString();
    }

    @GetMapping("/is/liked")
    public Boolean isLiked(@RequestParam(name = "no") Long postNo) {
        PostIsLikedDTO postIsLikedDTO = PostIsLikedDTO.builder()
                .postNo(postNo)
                .build();

        return postService.isLiked(postIsLikedDTO);
    }
}
