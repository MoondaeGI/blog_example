package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.liked.PostLiked;
import com.example.blog_example.model.domain.post.liked.PostLikedRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.model.dto.post.post.PostUpdateDTO;
import com.example.blog_example.model.vo.post.PostVO;
import com.example.blog_example.util.enums.state.LikedState;
import com.example.blog_example.util.enums.state.OpenState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public PostVO find(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.addViews();

        return PostVO.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByUser(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postRepository.findByUser(user).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByUpperCategory(Long upperCategoryNo) {
        UpperCategory upperCategory = upperCategoryRepository.findById(upperCategoryNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return postRepository.findByUpperCategory(upperCategory).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostVO> findByLowerCategory(Long lowerCategoryNo) {
        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(lowerCategoryNo)
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return postRepository.findByLowerCategory(lowerCategory).stream()
                .map(PostVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostVO> findPostLikedList(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postLikedRepository.findPostByUser(user).stream()
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

        Long lowerCategoryNo = (postSaveDTO.getLowerCategoryNo() != null) ?
                postSaveDTO.getLowerCategoryNo() : upperCategory.getLowerCategoryList().get(0).getLowerCategoryNo();
        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(lowerCategoryNo)
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
    public void delete(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        postRepository.delete(post);
    }

    @Transactional
    public LikedState changeLiked(Long postNo, Long userNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        if (Objects.equals(post.getUser().getUserNo(), userNo))
            throw new IllegalArgumentException("좋아요 하려는 유저는 이 게시글을 쓴 유저입니다.");

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));


        if (postLikedRepository.existsByPostAndUser(post, user)) {
            postLikedRepository.deleteByPost(post);
        } else {
            postLikedRepository.save(
                    PostLiked.builder()
                            .post(post)
                            .user(user)
                            .build());
        }

        return postLikedRepository.existsByPostAndUser(post, user) ? LikedState.LIKED : LikedState.CANCEL;
    }

    @Transactional
    public OpenState changeOpenYN(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return post.changeOpenYN();
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(Long postNo, Long userNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (Objects.equals(post.getUser().getUserNo(), userNo))
            throw new IllegalArgumentException("해당 번호를 가진 유저는 이 게시글을 쓴 유저입니다");

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return postLikedRepository.existsByPostAndUser(post, user);
    }

    @Transactional(readOnly = true)
    public Integer countLiked(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postLikedRepository.countByPost(post).intValue();
    }
}
