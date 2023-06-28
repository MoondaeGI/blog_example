package com.example.blog_example.model;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostLikedRepositoryTest {
    @Autowired private PostLikedRepository postLikedRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .email("test")
                        .password("test")
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

        Post post = postRepository.save(
                Post.builder()
                        .user(user)
                        .upperCategory(upperCategory)
                        .lowerCategory(lowerCategory)
                        .title("test")
                        .content("test")
                        .build());

        postLikedRepository.save(
                PostLiked.builder()
                        .user(user)
                        .post(post)
                        .build());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        upperCategoryRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void findPostByUserTest1() {
        User user = userRepository.findAll().get(0);

        Long postNo = postLikedRepository.findPostByUser(user).get(0).getPostNo();

        assertThat(postNo).isEqualTo(postRepository.findAll().get(0).getPostNo());
    }

    @Test
    public void findPostByUserTest2() {
        User user = userRepository.findAll().get(0);

        Post post = postRepository.save(
                Post.builder()
                        .user(user)
                        .upperCategory(upperCategoryRepository.findAll().get(0))
                        .lowerCategory(lowerCategoryRepository.findAll().get(0))
                        .title("test1")
                        .content("test1")
                        .build());

        postLikedRepository.save(
                PostLiked.builder()
                        .user(user)
                        .post(post)
                        .build());

        assertThat(postLikedRepository.findPostByUser(user).size()).isEqualTo(2);
    }

    @Test
    public void findPostByUserTest3() {
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test")
                        .password("test")
                        .build());

        postLikedRepository.save(
                PostLiked.builder()
                        .user(user)
                        .post(postRepository.findAll().get(0))
                        .build());

        assertThat(postLikedRepository.findPostByUser(user).size()).isEqualTo(1);
        assertThat(postLikedRepository.findPostByUser(userRepository.findAll().get(0)).size()).isEqualTo(1);
    }
}
