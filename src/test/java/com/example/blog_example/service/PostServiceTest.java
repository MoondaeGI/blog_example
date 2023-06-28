package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.post.liked.PostLiked;
import com.example.blog_example.model.domain.post.liked.PostLikedRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.post.post.*;
import com.example.blog_example.service.post.PostService;
import com.example.blog_example.util.enums.OpenYN;
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

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private PostLikedRepository postLikedRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .build());

        UpperCategory upperCategory = upperCategoryRepository.save(
                UpperCategory.builder()
                        .name("test")
                        .build());

        LowerCategory lowerCategory = lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test")
                        .upperCategory(upperCategory)
                        .build());

        postRepository.save(
                Post.builder()
                        .user(user)
                        .upperCategory(upperCategory)
                        .lowerCategory(lowerCategory)
                        .title("test")
                        .content("test")
                        .build());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void findAllTest() {
        Integer size = postRepository.findAll().size();

        assertThat(size).isEqualTo(postService.findAll().size());
    }

    @Test
    public void findTest() {
        Post post = postRepository.findAll().get(0);

        PostFindDTO postFindDTO = new PostFindDTO(post.getPostNo());

        assertThat(post.getTitle()).isEqualTo(postService.find(postFindDTO).getTitle());
    }

    @Test
    public void findByObjectTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        PostFindByObjectDTO postFindByUserDTO = new PostFindByObjectDTO(userNo);
        PostFindByObjectDTO postFindByUpperCategoryDTO = new PostFindByObjectDTO(upperCategoryNo);
        PostFindByObjectDTO postFindByLowerCategoryDTO = new PostFindByObjectDTO(lowerCategoryNo);

        assertThat(userNo).isEqualTo(
                postService.findByUser(postFindByUserDTO).get(0).getUserVO().getUserNo());
        assertThat(upperCategoryNo).isEqualTo(
                postService.findByUpperCategory(postFindByUpperCategoryDTO).get(0)
                        .getUpperCategoryVO().getUpperCategoryNo());
        assertThat(lowerCategoryNo).isEqualTo(
                postService.findByLowerCategory(postFindByLowerCategoryDTO).get(0)
                        .getLowerCategoryVO().getLowerCategoryNo());
    }

    @Test
    public void saveTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        PostSaveDTO postSaveDTO = new PostSaveDTO(upperCategoryNo, lowerCategoryNo, userNo, "test", "test");

        Long postNo = postService.save(postSaveDTO);

        assertThat(postNo).isEqualTo(postRepository.findAll().get(1).getPostNo());
    }

    @Test
    public void updateTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        String title = "test1";
        String content = "test1";

        PostUpdateDTO postUpdateDTO = new PostUpdateDTO(postNo, upperCategoryNo, lowerCategoryNo, title, content);

        postService.update(postUpdateDTO);

        assertThat(title).isEqualTo(postRepository.findAll().get(0).getTitle());
        assertThat(content).isEqualTo(postRepository.findAll().get(0).getContent());
    }

    @Test
    public void deleteTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();

        PostDeleteDTO postDeleteDTO = new PostDeleteDTO(postNo);

        postService.delete(postDeleteDTO);

        assertThat(postRepository.findById(postNo)).isEmpty();
    }

    @Test
    public void addViewsTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();

        PostAddViewsDTO postAddViewsDTO = new PostAddViewsDTO(postNo);

        assertThat(postService.addViews(postAddViewsDTO)).isEqualTo(1);
        assertThat(postService.addViews(postAddViewsDTO)).isEqualTo(2);
    }

    @Test
    public void changeOpenYNTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();

        PostChangeOpenYNDTO postChangeOpenYNDTO = new PostChangeOpenYNDTO(postNo);

        assertThat(postService.changeOpenYN(postChangeOpenYNDTO)).isEqualTo(OpenYN.CLOSE);
        assertThat(postService.changeOpenYN(postChangeOpenYNDTO)).isEqualTo(OpenYN.OPEN);
    }

    @Test
    public void isLikedTest() {
        Post post = postRepository.findAll().get(0);
        Long postNo = post.getPostNo();

        PostIsLikedDTO postIsLikedDTO = new PostIsLikedDTO(postNo);

        assertThat(postService.isLiked(postIsLikedDTO)).isFalse();

        postLikedRepository.save(
                PostLiked.builder()
                        .post(post)
                        .user(userRepository.findAll().get(0))
                        .build());

        assertThat(postService.isLiked(postIsLikedDTO)).isTrue();
    }
}
