package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.post.liked.PostLiked;
import com.example.blog_example.model.domain.post.liked.PostLikedRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.post.liked.PostLikedCountDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedDeleteDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedFindPostByUserDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.model.vo.post.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostLikedService {
    private final PostLikedRepository postLikedRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PostVO> findPostByUser(PostLikedFindPostByUserDTO postLikedFindPostByUserDTO) {
        User user = userRepository.findById(postLikedFindPostByUserDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postLikedRepository.findPostByUser(user).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Integer countByPost(PostLikedCountDTO postLikedCountDTO) {
        Post post = postRepository.findById(postLikedCountDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postLikedRepository.countByPost(post).intValue();
    }

    @Transactional
    public Long save(PostLikedSaveDTO postLikedSaveDTO) {
        Post post = postRepository.findById(postLikedSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        User user = userRepository.findById(postLikedSaveDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postLikedRepository.save(
                PostLiked.builder()
                        .post(post)
                        .user(user)
                        .build())
                .getPostLikedNo();
    }

    @Transactional
    public void delete(PostLikedDeleteDTO postLikedDeleteDTO) {
        Post post = postRepository.findById(postLikedDeleteDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        postLikedRepository.deleteByPost(post);
    }
}
