package com.example.blog_example.controller;

import com.example.blog_example.model.dto.post.post.PostSearchDTO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.model.vo.user.UserVO;
import com.example.blog_example.service.category.UpperCategoryService;
import com.example.blog_example.service.comment.CommentService;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostService;
import com.example.blog_example.service.user.BlogVisitCountService;
import com.example.blog_example.service.user.UserService;
import com.example.blog_example.util.exception.NotMatchUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.PositiveOrZero;

@Tag(name = "page", description = "페이지 API")
@Validated
@RequiredArgsConstructor
@RestController
public class PageController {
    private final UserService userService;
    private final BlogVisitCountService blogVisitCountService;
    private final UpperCategoryService upperCategoryService;
    private final PostService postService;
    private final FileService fileService;
    private final CommentService commentService;
    private final HttpSession httpSession;

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

    @Operation(summary = "메인 화면 출력", description = "메인 화면을 가져오는 API")
    @GetMapping("/")
    public String index(Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) model.addAttribute("user", user);

        model.addAttribute("posts", postService.findAll());

        return "index";
    }

    @Operation(summary = "검색 화면 출력", description = "검색 화면을 가져오는 API")
    @Parameters({
            @Parameter(name = "title", description = "제목", example = "example", in = ParameterIn.QUERY),
            @Parameter(name = "content", description = "내용", example = "example", in = ParameterIn.QUERY)
    })
    @GetMapping("/search")
    public String searchPage(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = false) String content,
            Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) model.addAttribute("user", user);

        PostSearchDTO postSearchDTO = PostSearchDTO.builder()
                .title(title)
                .content(content)
                .build();
        model.addAttribute("posts", postService.search(postSearchDTO));

        return "search";
    }

    @Operation(summary = "유저 화면 출력", description = "해당 번호를 가진 유저의 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/user-page")
    public String userPage(
            @RequestParam("no") @PositiveOrZero Long userNo,
            Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) model.addAttribute("user", user);

        model.addAttribute("blogger", userService.find(userNo));
        model.addAttribute("liked-posts", postService.findPostLikedList(userNo));
        model.addAttribute("comments", commentService.findByUser(userNo));

        return "user-page";
    }

    @Operation(summary = "블로그 화면 출력", description = "해당 번호를 가진 유저의 블로그 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/blog-page")
    public String blogPage(
            @RequestParam("no") @PositiveOrZero Long bloggerNo,
            Model model) {
        model.addAttribute("blogger", userService.find(bloggerNo));
        model.addAttribute("categories", upperCategoryService.findAll(bloggerNo));
        model.addAttribute("posts", postService.findByUser(bloggerNo));

        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            if (!blogVisitCountService.isVisit(user.getUserNo(), bloggerNo))
                blogVisitCountService.addVisitCount(bloggerNo);
        }

        return "blog-page";
    }

    @Operation(summary = "게시글 화면 출력", description = "해당 번호를 가진 게시글 화면을 가져오는 API")
    @Parameter(name = "postNo", description = "게시글 번호", example = "1", required = true)
    @GetMapping("/post-page")
    public String post(
            @RequestParam("no") @PositiveOrZero Long postNo,
            Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("post", postService.find(postNo));
        model.addAttribute("liked-count", postService.countLiked(postNo));
        model.addAttribute("comments", commentService.findByPost(postNo));
        if(fileService.isExist(postNo))
            model.addAttribute("files", fileService.findByPost(postNo));

        return "post-page";
    }

    @Operation(summary = "게시글 작성 화면 출력", description = "게시글을 작성하기 위한 화면을 가져오는 API")
    @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true)
    @GetMapping("/post-save")
    public String postSave(
            @RequestParam("no") @PositiveOrZero Long userNo,
            Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            if (blogVisitCountService.isVisit(user.getUserNo(), userNo))
                throw new NotMatchUserException("해당 유저가 작성한 게시글이 아닙니다.");
        }

        model.addAttribute("blogger", userService.find(userNo));
        model.addAttribute("categories", upperCategoryService.findAll(userNo));

        return "post-save";
    }

    @Operation(summary = "게시글 수정 화면 출력", description = "해당 번호를 가진 게시글을 수정하는 화면을 가져오는 API")
    @Parameters({
            @Parameter(name = "userNo", description = "유저 번호", example = "1", required = true),
            @Parameter(name = "postNo", description = "게시글 번호", example = "1", required = true)
    })
    @GetMapping("/post-update")
    public String postUpdate(
            @RequestParam("user-no") @PositiveOrZero Long userNo,
            @RequestParam("post-no") @PositiveOrZero Long postNo,
            Model model) {
        UserVO user = (UserVO) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            if (blogVisitCountService.isVisit(user.getUserNo(), userNo))
                throw new NotMatchUserException("해당 유저가 작성한 게시글이 아닙니다.");
        }

        model.addAttribute("blogger", userService.find(userNo));
        model.addAttribute("categories", upperCategoryService.findAll(userNo));
        model.addAttribute("post", postService.find(postNo));
        if(fileService.isExist(postNo))
            model.addAttribute("files", fileService.findByPost(postNo));

        return "post-update";
    }
}
