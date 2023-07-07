package com.example.blog_example.controller;

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
import com.example.blog_example.model.vo.post.PostVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class PostControllerTest {
    private final String URL = "/post";

    @Autowired private TestRestTemplate testRestTemplate;

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
        ResponseEntity<List<PostVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PostVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(postRepository.findAll().size());
    }

    @Test
    public void findTest() {
        Post post = postRepository.findAll().get(0);

        ResponseEntity<PostVO> responseEntity = testRestTemplate
                .getForEntity(URL + "?no=" + post.getPostNo(), PostVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void findByUserTest() {
        User user = userRepository.findAll().get(0);

        ResponseEntity<List<PostVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list/user?no=" + user.getUserNo(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PostVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(postRepository.findByUser(user).size());
    }

    @Test
    public void findByUpperCategoryTest() {
        UpperCategory upperCategory = upperCategoryRepository.findAll().get(0);

        ResponseEntity<List<PostVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list/upper-category?no=" + upperCategory.getUpperCategoryNo(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<PostVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(postRepository.findByUpperCategory(upperCategory).size());
    }

    @Test
    public void findByLowerCategoryTest() {
        LowerCategory lowerCategory = lowerCategoryRepository.findAll().get(0);

        ResponseEntity<List<PostVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list/lower-category?no=" + lowerCategory.getLowerCategoryNo(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<PostVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(postRepository.findByLowerCategory(lowerCategory).size());
    }

    @Test
    public void findPostLikedListTest() {
        postLikedRepository.save(
                PostLiked.builder()
                        .post(postRepository.findAll().get(0))
                        .user(userRepository.findAll().get(0))
                        .build());
    }
}
