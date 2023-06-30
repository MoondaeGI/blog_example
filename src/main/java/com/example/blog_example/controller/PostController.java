package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.dto.post.file.FileUpdateDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.dto.post.post.PostChangeLikedDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.post.PostDetailVO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostLikedService;
import com.example.blog_example.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    private final PostLikedService postLikedService;
    private final FileService fileService;

    @GetMapping("/info/list")
    public List<PostVO> findAll() {
        return postService.findAll();
    }

    @GetMapping("/info")
    public PostDetailVO find(@RequestParam(name = "no") @PositiveOrZero Long postNo) {
        postService.addViews(postNo);

        return PostDetailVO.of(postService.find(postNo), fileService.findByPost(postNo));
    }

    @GetMapping("/info/user")
    public List<PostVO> findByUser(@RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return postService.findByUser(userNo);
    }

    @GetMapping("/info/upper")
    public List<PostVO> findByUpperCategory(@RequestParam(name = "no") @PositiveOrZero Long upperCategoryNo) {
        return postService.findByUpperCategory(upperCategoryNo);
    }

    @GetMapping("/info/lower")
    public List<PostVO> findByLowerCategory(@RequestParam(name = "no") @PositiveOrZero Long lowerCategoryNo) {
        return postService.findByLowerCategory(lowerCategoryNo);
    }

    @GetMapping("/info/liked/user")
    public List<PostVO> findPostLiked(@RequestParam(name = "no") @PositiveOrZero Long userNo) {
        return postLikedService.findPostByUser(userNo);
    }

    @PostMapping("/save")
    public Long save(@RequestBody PostSaveDTO postSaveDTO, List<MultipartFile> multipartFiles) {
        Long postNo = postService.save(postSaveDTO);

        if (!multipartFiles.isEmpty()) {
            FileSaveDTO fileSaveDTO = FileSaveDTO.builder()
                    .multipartFiles(multipartFiles)
                    .postNo(postNo)
                    .build();
            fileService.save(fileSaveDTO);
        }

        return postNo;
    }

    @PutMapping("/update")
    public Long update(@RequestBody PostUpdateDTO postUpdateDTO, List<MultipartFile> multipartFiles) {
        if (!multipartFiles.isEmpty()) {
            FileUpdateDTO fileUpdateDTO = FileUpdateDTO.builder()
                    .postNo(postUpdateDTO.getPostNo())
                    .multipartFiles(multipartFiles)
                    .build();
            fileService.update(fileUpdateDTO);
        }
        return postService.update(postUpdateDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "no") @PositiveOrZero Long postNo) {
        fileService.deleteByPost(postNo);
        postService.delete(postNo);
    }

    @GetMapping("/state/liked")
    public Boolean changeLiked(
            @RequestParam("post") @PositiveOrZero Long postNo, @RequestParam("user") @PositiveOrZero Long userNo) {
        if (postService.isLiked(postNo)) {
            postLikedService.delete(postNo);

            return false;
        }

        PostLikedSaveDTO postLikedSaveDTO = PostLikedSaveDTO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();
        postLikedService.save(postLikedSaveDTO);

        return true;
    }

    @GetMapping("/state/open")
    public String changeOpenYN(@RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postService.changeOpenYN(postNo).toString();
    }

    @GetMapping("/liked")
    public Boolean isLiked(@RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postService.isLiked(postNo);
    }

    @GetMapping("/liked/count")
    public Integer countLiked(@RequestParam(name = "no") @PositiveOrZero Long postNo) {
        return postLikedService.countByPost(postNo);
    }
}
