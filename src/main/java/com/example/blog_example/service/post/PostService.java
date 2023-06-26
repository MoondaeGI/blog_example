package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.post.postLiked.PostLikedRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.post.post.*;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.util.enums.OpenYN;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UpperCategoryRepository upperCategoryRepository;
    private final LowerCategoryRepository lowerCategoryRepository;
    private final PostLikedRepository postLikedRepository;

    @Transactional(readOnly = true)
    public List<PostVO> findAll() {
        return postRepository.findAll().stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostVO find(PostFindDTO postFindDTO) {
        return PostVO.from(
                postRepository.findById(postFindDTO.getPostNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByUser(PostFindByObjectDTO postFindByObjectDTO) {
        User user = userRepository.findById(postFindByObjectDTO.getObjectNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postRepository.findByUser(user).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByUpperCategory(PostFindByObjectDTO postFindByObjectDTO) {
        UpperCategory upperCategory =
                upperCategoryRepository.findById(postFindByObjectDTO.getObjectNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return postRepository.findByUpperCategory(upperCategory).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByLowerCategory(PostFindByObjectDTO postFindByObjectDTO) {
        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(postFindByObjectDTO.getObjectNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return postRepository.findByLowerCategory(lowerCategory).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(PostSaveDTO postSaveDTO) {
        User user = userRepository.findById(postSaveDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        UpperCategory upperCategory =
                upperCategoryRepository.findById(postSaveDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(postSaveDTO.getLowerCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return postRepository.save(
                Post.builder()
                        .user(user)
                        .upperCategory(upperCategory)
                        .lowerCategory(lowerCategory)
                        .title(postSaveDTO.getTitle())
                        .content(postSaveDTO.getContent())
                        .build())
                .getPostNo();
    }

    @Transactional
    public Long update(PostUpdateDTO postUpdateDTO) {
        Post post = postRepository.findById(postUpdateDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        UpperCategory upperCategory =
                upperCategoryRepository.findById(postUpdateDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(postUpdateDTO.getLowerCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        post.update(upperCategory, lowerCategory, postUpdateDTO.getTitle(), postUpdateDTO.getContent());

        return post.getPostNo();
    }

    @Transactional
    public void delete(PostDeleteDTO postDeleteDTO) {
        Post post = postRepository.findById(postDeleteDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        postRepository.delete(post);
    }

    @Transactional
    public Integer addViews(PostAddViewsDTO postAddViewsDTO) {
        Post post = postRepository.findById(postAddViewsDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return post.addViews();
    }

    @Transactional
    public OpenYN changeOpenYN(PostChangeOpenYNDTO postChangeOpenYNDTO) {
        Post post = postRepository.findById(postChangeOpenYNDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return post.changeOpenYN();
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(PostIsLikedDTO postIsLikedDTO) {
        Post post = postRepository.findById(postIsLikedDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postLikedRepository.findByPost(post) != null;
    }
}
