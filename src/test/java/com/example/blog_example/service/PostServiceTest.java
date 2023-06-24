package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.model.domain.user.UserRepository;
import com.example.blog_example.model.dto.post.detail.PostDetailFindDTO;
import com.example.blog_example.model.dto.post.detail.PostDetailSaveDTO;
import com.example.blog_example.model.dto.post.post.PostFindByObjectDTO;
import com.example.blog_example.model.dto.post.post.PostFindDTO;
import com.example.blog_example.model.dto.post.post.PostSaveDTO;
import com.example.blog_example.service.post.PostDetailService;
import com.example.blog_example.service.post.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PostServiceTest {
    @Autowired private PostService postService;
    @Autowired private PostDetailService postDetailService;

    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostDetailRepository postDetailRepository;

    @BeforeEach
    public void setup() {
        UpperCategory upperCategory =
                upperCategoryRepository.save(
                        UpperCategory.builder()
                                .name("test")
                                .build());

        LowerCategory lowerCategory = lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test")
                        .upperCategory(upperCategory)
                        .build());

        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .build());

        Post post = Post.builder()
                .upperCategory(upperCategory)
                .lowerCategory(lowerCategory)
                .user(user)
                .build();

        postDetailRepository.save(
                PostDetail.builder()
                        .title("test")
                        .content("test")
                        .post(post)
                        .build());
    }

    @AfterEach
    public void tearDown() {
        upperCategoryRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void postFindAllTest() {
        Integer size = postService.findAll().size();

        assertThat(size).isEqualTo(postRepository.findAll().size());
    }

    @Test
    public void postDetailAllTest() {
        Integer size = postDetailService.findAll().size();

        assertThat(size).isEqualTo(postDetailRepository.findAll().size());
    }

    @Test
    public void postFindTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();

        PostFindDTO postFindDTO = new PostFindDTO(postNo);

        assertThat(postNo).isEqualTo(postService.find(postFindDTO).getPostNo());
    }

    @Test
    public void postDetailFindTest() {
        Long postDetailNo = postDetailRepository.findAll().get(0).getPostDetailNo();

        PostDetailFindDTO postDetailFindDTO = new PostDetailFindDTO(postDetailNo);

        assertThat(postDetailNo).isEqualTo(postDetailService.find(postDetailFindDTO).getPostDetailNo());
    }

    @Test
    public void postFindByObjectTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        PostFindByObjectDTO postFindByUpperCategoryDTO = new PostFindByObjectDTO(upperCategoryNo);
        PostFindByObjectDTO postFindByLowerCategoryDTO = new PostFindByObjectDTO(lowerCategoryNo);
        PostFindByObjectDTO postFindByUserDTO = new PostFindByObjectDTO(userNo);

        assertThat(upperCategoryNo).isEqualTo(
                postService.findByUpperCategory(postFindByUpperCategoryDTO).get(0)
                        .getUpperCategoryVO().getUpperCategoryNo());
        assertThat(lowerCategoryNo).isEqualTo(
                postService.findByLowerCategory(postFindByLowerCategoryDTO).get(0)
                        .getLowerCategoryVO().getLowerCategoryNo());
        assertThat(userNo).isEqualTo(
                postService.findByUser(postFindByUserDTO).get(0).getUserVO().getUserNo());
    }

    @Test
    public void postSaveTest() {
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