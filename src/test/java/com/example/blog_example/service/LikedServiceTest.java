package com.example.blog_example.service;

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
import com.example.blog_example.model.domain.post.liked.PostLiked;
import com.example.blog_example.model.domain.post.liked.PostLikedRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.liked.CommentLikedCountDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedDeleteDTO;
import com.example.blog_example.model.dto.comment.liked.CommentLikedSaveDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedCountDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedDeleteDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedFindPostByUserDTO;
import com.example.blog_example.model.dto.post.liked.PostLikedSaveDTO;
import com.example.blog_example.service.comment.CommentLikedService;
import com.example.blog_example.service.post.PostLikedService;
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
public class LikedServiceTest {
    @Autowired private PostLikedService postLikedService;
    @Autowired private CommentLikedService commentLikedService;

    @Autowired private CommentLikedRepository commentLikedRepository;
    @Autowired private PostLikedRepository postLikedRepository;

    @Autowired private CommentRepository commentRepository;
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

        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .content("test")
                        .build());

        postLikedRepository.save(
                PostLiked.builder()
                        .user(user)
                        .post(post)
                        .build());

        commentLikedRepository.save(
                CommentLiked.builder()
                        .user(user)
                        .comment(comment)
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
    public void countByPostTest() {
        Post post = postRepository.findAll().get(0);
        Long postNo = post.getPostNo();

        PostLikedCountDTO postLikedCountDTO = new PostLikedCountDTO(postNo);

        assertThat(postLikedService.countByPost(postLikedCountDTO)).isEqualTo(1);

        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1")
                        .password("test1")
                        .build());

        postLikedRepository.save(
                PostLiked.builder()
                        .user(user)
                        .post(post)
                        .build());

        assertThat(postLikedService.countByPost(postLikedCountDTO)).isEqualTo(2);
    }

    @Test
    public void countByCommentTest() {
        Comment comment = commentRepository.findAll().get(0);
        Long commentNo = comment.getCommentNo();

        CommentLikedCountDTO commentLikedCountDTO = new CommentLikedCountDTO(commentNo);

        assertThat(commentLikedService.countByComment(commentLikedCountDTO)).isEqualTo(1);

        User user = userRepository.save(
                User.builder()
                        .name("test1")
                        .blogName("test1")
                        .email("test1")
                        .password("test1")
                        .build());

        commentLikedRepository.save(
                CommentLiked.builder()
                        .user(user)
                        .comment(comment)
                        .build());

        assertThat(commentLikedService.countByComment(commentLikedCountDTO)).isEqualTo(2);
    }

    @Test
    public void postLikedSaveTest() {
        Long postNo = postRepository.findAll().get(0).getPostNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        PostLikedSaveDTO postLikedSaveDTO = new PostLikedSaveDTO(postNo, userNo);

        assertThat(postLikedService.save(postLikedSaveDTO))
                .isEqualTo(postLikedRepository.findAll().get(1).getPostLikedNo());
    }

    @Test
    public void commentLikedSaveTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        CommentLikedSaveDTO commentLikedSaveDTO = new CommentLikedSaveDTO(commentNo, userNo);

        assertThat(commentLikedService.save(commentLikedSaveDTO))
                .isEqualTo(commentLikedRepository.findAll().get(1).getCommentLikedNo());
    }

    @Test
    public void postLikedDeleteTest() {
        Post post = postRepository.findAll().get(0);
        Long postNo = post.getPostNo();

        PostLikedDeleteDTO postLikedDeleteDTO = new PostLikedDeleteDTO(postNo);

        postLikedService.delete(postLikedDeleteDTO);

        assertThat(postLikedRepository.findByPost(post)).isNull();
    }

    @Test
    public void commentLikedDeleteTest() {
        Comment comment = commentRepository.findAll().get(0);
        Long commentNo = comment.getCommentNo();

        CommentLikedDeleteDTO commentLikedDeleteDTO = new CommentLikedDeleteDTO(commentNo);

        commentLikedService.delete(commentLikedDeleteDTO);

        assertThat(commentLikedRepository.findByComment(comment)).isNull();
    }

    @Test
    public void findPostByUserTest() {
        User user = userRepository.findAll().get(0);
        Long userNo = user.getUserNo();

        PostLikedFindPostByUserDTO postLikedFindPostByUserDTO = new PostLikedFindPostByUserDTO(userNo);

        assertThat(postLikedService.findPostByUser(postLikedFindPostByUserDTO).size()).isEqualTo(1);

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

        assertThat(postLikedService.findPostByUser(postLikedFindPostByUserDTO).size()).isEqualTo(2);
    }
}
