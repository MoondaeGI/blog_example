package com.example.blog_example.controller;

import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostLikedService;
import com.example.blog_example.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    private final PostLikedService postLikedService;
    private final FileService fileService;
}
