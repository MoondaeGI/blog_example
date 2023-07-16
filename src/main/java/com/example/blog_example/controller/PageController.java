package com.example.blog_example.controller;

import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import com.example.blog_example.service.comment.CommentService;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostService;
import com.example.blog_example.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;

@Tag(name = "page", description = "페이지 API")
@Validated
@RequiredArgsConstructor
@RestController
public class PageController {
    private final UserService userService;
    private final UpperCategoryService upperCategoryService;
    private final PostService postService;
    private final FileService fileService;
    private final CommentService commentService;

    @Operation(summary = "메인 화면 출력", description = "메인 화면을 가져오는 API")
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());

        return "index";
    }

    @Operation(summary = "로그인 화면 출력", description = "로그인 화면을 가져오는 API")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Operation(summary = "회원 가입 화면 출력", description = "회원 가입 화면을 가져오는 API")
    @GetMapping("/setup")
    public String setup() {
        return "setup";
    }

    @Operation(summary = "유저 화면 출력", description = "해당 번호를 가진 유저의 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/user")
    public String userPage(
            @RequestParam("no") @PositiveOrZero Long userNo,
            Model model) {
        model.addAttribute("user", userService.find(userNo));
        model.addAttribute("liked-posts", postService.findPostLikedList(userNo));
        model.addAttribute("comments", commentService.findByUser(userNo));

        return "user-page";
    }

    @Operation(summary = "블로그 화면 출력", description = "해당 번호를 가진 유저의 블로그 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/blog")
    public String blogPage(
            @RequestParam("no") @PositiveOrZero Long userNo,
            Model model) {
        model.addAttribute("user", userService.find(userNo));
        model.addAttribute("categories", upperCategoryService.findAll(userNo));
        model.addAttribute("posts", postService.findByUser(userNo));

        return "blog-page";
    }

    @Operation(summary = "게시글 화면 출력", description = "해당 번호를 가진 게시글 화면을 가져오는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", required = true)
    @GetMapping("/post")
    public String post(
            @RequestParam("no") @PositiveOrZero Long postNo,
            Model model) {
        model.addAttribute("post", postService.find(postNo));
        model.addAttribute("comments", commentService.findByPost(postNo));
        if(fileService.isExist(postNo))
            model.addAttribute("files", fileService.findByPost(postNo));

        return "post";
    }

    @Operation(summary = "게시글 작성 화면 출력", description = "게시글을 작성하기 위한 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/post-save")
    public String postSave(
            @RequestParam("no") @PositiveOrZero Long userNo,
            Model model) {
        model.addAttribute("user", userService.find(userNo));
        model.addAttribute("categories", upperCategoryService.findAll(userNo));

        return "post-save";
    }

    @Operation(summary = "게시글 수정 화면 출력", description = "해당 번호를 가진 게시글을 수정하는 화면을 가져오는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", required = true)
    @GetMapping("/post-update")
    public String postUpdate(
            @RequestParam("no") @PositiveOrZero Long postNo,
            Model model) {
        model.addAttribute("post", postService.find(postNo));
        if(fileService.isExist(postNo))
            model.addAttribute("files", fileService.findByPost(postNo));

        return "post-update";
    }
}
