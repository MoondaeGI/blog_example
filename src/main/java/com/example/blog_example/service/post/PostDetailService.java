package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.dto.post.detail.PostDetailAddViewsDTO;
import com.example.blog_example.model.dto.post.detail.PostDetailFindDTO;
import com.example.blog_example.model.dto.post.detail.PostDetailSaveDTO;
import com.example.blog_example.model.dto.post.detail.PostDetailUpdateDTO;
import com.example.blog_example.model.vo.post.PostDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostDetailService {
    private final PostDetailRepository postDetailRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostDetailVO> findAll() {
        return postDetailRepository.findAll().stream()
                .map(PostDetailVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDetailVO find(PostDetailFindDTO postDetailFindDTO) {
        return PostDetailVO.from(
                postDetailRepository.findById(postDetailFindDTO.getPostDetailNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.")));
    }

    @Transactional
    public Long save(PostDetailSaveDTO postDetailSaveDTO) {
        Post post = postRepository.findById(postDetailSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postDetailRepository.save(
                PostDetail.builder()
                        .post(post)
                        .title(postDetailSaveDTO.getTitle())
                        .content(postDetailSaveDTO.getContent())
                        .build())
                .getPostDetailNo();
    }

    @Transactional
    public Long update(PostDetailUpdateDTO postDetailUpdateDTO) {
        PostDetail postDetail =
                postDetailRepository.findById(postDetailUpdateDTO.getPostDetailNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        postDetail.update(postDetailUpdateDTO.getTitle(), postDetailUpdateDTO.getContent());

        return postDetail.getPostDetailNo();
    }

    @Transactional
    public Integer addViews(PostDetailAddViewsDTO postDetailAddViewsDTO) {
        PostDetail postDetail =
                postDetailRepository.findById(postDetailAddViewsDTO.getPostDetailNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postDetail.addViews();
    }
}
