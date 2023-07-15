package com.example.blog_example.controller;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.comment.comment.CommentRepository;
import com.example.blog_example.model.domain.comment.liked.CommentLiked;
import com.example.blog_example.model.domain.comment.liked.CommentLikedRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.CommentUpdateDTO;
import com.example.blog_example.model.vo.enums.EnumStateVO;
import com.example.blog_example.model.vo.post.CommentVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CommentControllerTest {
    private final String URL = "/comment";

    @Autowired private TestRestTemplate testRestTemplate;

    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private CommentLikedRepository commentLikedRepository;

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
                        .user(user)
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

        commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .content("test")
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
    public void findAllTest() {
        ResponseEntity<List<CommentVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<CommentVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(commentRepository.findAll().size());
    }

    @Test
    public void findTest() {
        Comment comment = commentRepository.findAll().get(0);

        ResponseEntity<CommentVO> responseEntity = testRestTemplate
                .getForEntity(URL + "?no=" + comment.getCommentNo(), CommentVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(comment.getContent());
    }

    @Test
    public void findByPostTest() {
        Post post = postRepository.findAll().get(0);

        ResponseEntity<List<CommentVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list/post?no=" + post.getPostNo(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<CommentVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(commentRepository.findByPost(post).size());
    }

    @Test
    public void findByUserTest() {
        User user = userRepository.findAll().get(0);

        ResponseEntity<List<CommentVO>> responseEntity = testRestTemplate
                .exchange(URL + "/list/post?no=" + user.getUserNo(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<CommentVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(commentRepository.findByUser(user).size());
    }

    @Test
    public void saveTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        CommentSaveDTO commentSaveDTO = CommentSaveDTO.builder()
                .userNo(userNo)
                .postNo(postNo)
                .content("test1")
                .build();

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(URL, commentSaveDTO, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commentRepository.findById(responseEntity.getBody())).isNotNull();
    }

    @Test
    public void updateTest() {
        String content = "test1";
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        CommentUpdateDTO commentUpdateDTO = CommentUpdateDTO.builder()
                .commentNo(commentNo)
                .content(content)
                .build();
        HttpEntity<CommentUpdateDTO> requestEntity = new HttpEntity<>(commentUpdateDTO);

        ResponseEntity<Long> responseEntity = testRestTemplate
                .exchange(URL, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Long>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commentRepository.findById(responseEntity.getBody())
                .orElseThrow(IllegalArgumentException::new)
                .getContent())
                .isEqualTo(content);
    }

    @Test
    public void deleteTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        ResponseEntity<Void> responseEntity = testRestTemplate
                .exchange(URL + "no?=" + commentNo, HttpMethod.DELETE, new HttpEntity<>(commentNo), Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commentRepository.findById(commentNo)).isEmpty();
    }

    @DisplayName("좋아요 표시가 되어 있을때의 동작 확인")
    @Test
    public void changeLikedTest1() {
        Comment comment = commentRepository.findAll().get(0);
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1234123@test.com")
                        .password("jdklsjdofi1@")
                        .build());

        commentLikedRepository.save(
                CommentLiked.builder()
                        .comment(comment)
                        .user(user)
                        .build());

        String url = String.format(URL + "/state/liked?comment-no=%d&user-no=%d", comment.getCommentNo(), user.getUserNo());

        ResponseEntity<EnumStateVO> responseEntity = testRestTemplate.getForEntity(url, EnumStateVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getState()).isEqualTo("CANCEL");
    }

    @DisplayName("좋아요 표시가 안되어 있을때의 동작 확인")
    @Test
    public void changeLikedTest2() {
        Comment comment = commentRepository.findAll().get(0);
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1234123@test.com")
                        .password("jdklsjdofi1@")
                        .build());

        String url = String.format(URL + "/state/liked?comment-no=%d&user-no=%d", comment.getCommentNo(), user.getUserNo());

        ResponseEntity<EnumStateVO> responseEntity = testRestTemplate.getForEntity(url, EnumStateVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getState()).isEqualTo("LIKED");
    }

    @DisplayName("좋아요 표시가 되어 있을때의 동작 확인")
    @Test
    public void isLikedTest1() {
        Comment comment = commentRepository.findAll().get(0);
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1234123@test.com")
                        .password("jdklsjdofi1@")
                        .build());

        commentLikedRepository.save(
                CommentLiked.builder()
                        .comment(comment)
                        .user(user)
                        .build());

        String url = String.format(URL + "/liked?comment-no=%d&user-no=%d", comment.getCommentNo(), user.getUserNo());

        ResponseEntity<Boolean> responseEntity = testRestTemplate.getForEntity(url, Boolean.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(Boolean.TRUE);
    }

    @DisplayName("좋아요 표시가 안되어 있을때의 동작 확인")
    @Test
    public void isLikedTest2() {
        Comment comment = commentRepository.findAll().get(0);
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1234123@test.com")
                        .password("jdklsjdofi1@")
                        .build());

        String url = String.format(URL + "/liked?comment-no=%d&user-no=%d", comment.getCommentNo(), user.getUserNo());

        ResponseEntity<Boolean> responseEntity = testRestTemplate.getForEntity(url, Boolean.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void countLikedTest() {
        Comment comment = commentRepository.findAll().get(0);
        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1234123@test.com")
                        .password("jdklsjdofi1@")
                        .build());

        commentLikedRepository.save(
                CommentLiked.builder()
                        .comment(comment)
                        .user(user)
                        .build());

        ResponseEntity<Integer> responseEntity = testRestTemplate
                .getForEntity(URL + "/liked/count?no=" + comment.getCommentNo(), Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(1);
    }
}
