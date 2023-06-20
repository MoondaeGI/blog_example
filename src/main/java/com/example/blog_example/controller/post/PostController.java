package com.example.blog_example.controller.post;

import com.example.blog_example.service.post.CommentService;
import com.example.blog_example.service.post.FileService;
import com.example.blog_example.service.post.PostDetailService;
import com.example.blog_example.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final PostDetailService postDetailService;
    private final FileService fileService;
    private final CommentService commentService;
}
