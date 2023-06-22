package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.model.domain.user.UserRepository;
import com.example.blog_example.model.dto.post.detail.PostDetailSaveDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.service.post.PostDetailService;
import com.example.blog_example.service.post.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostDetailServiceTest {
    @Autowired private PostDetailService postDetailService;
    @Autowired private PostService postService;

    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        UpperCategory upperCategory =
                upperCategoryRepository.save(
                        UpperCategory.builder()
                                .name("test")
                                .build());

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test")
                        .upperCategory(upperCategory)
                        .build());

        userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .build());
    }

    @AfterEach
    public void tearDown() {
        upperCategoryRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        PostSaveDTO postSaveDTO = new PostSaveDTO(upperCategoryNo, lowerCategoryNo, userNo);
        Long postNo = postService.save(postSaveDTO);

        PostDetailSaveDTO postDetailSaveDTO = new PostDetailSaveDTO(postNo, "test", "test");
        Long postDetailNo = postDetailService.save(postDetailSaveDTO);

        assertThat(postDetailNo).isEqualTo(postNo);
    }
}
