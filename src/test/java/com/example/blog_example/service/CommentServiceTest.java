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
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.comment.*;
import com.example.blog_example.service.comment.CommentService;
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
public class CommentServiceTest {
    @Autowired private CommentService commentService;

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
        Integer size = commentRepository.findAll().size();

        assertThat(size).isEqualTo(commentService.findAll().size());
    }

    @Test
    public void findTest() {
        Comment comment = commentRepository.findAll().get(0);
        Long commentNo = comment.getCommentNo();

        CommentFindDTO commentFindDTO = new CommentFindDTO(commentNo);

        assertThat(commentService.find(commentFindDTO).getContent()).isEqualTo(comment.getContent());
    }

    @Test
    public void findByUserTest() {
        String content = commentRepository.findAll().get(0).getContent();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        CommentFindByObjectDTO commentFindByUser = new CommentFindByObjectDTO(userNo);

        assertThat(content).isEqualTo(commentService.findByUser(commentFindByUser).get(0).getContent());
    }

    @Test
    public void findByPostTest() {
        String content = commentRepository.findAll().get(0).getContent();
        Long postNo = postRepository.findAll().get(0).getPostNo();

        CommentFindByObjectDTO commentFindByPost = new CommentFindByObjectDTO(postNo);

        assertThat(content).isEqualTo(commentService.findByPost(commentFindByPost).get(0).getContent());
    }

    @Test
    public void saveTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();
        Long postNo = postRepository.findAll().get(0).getPostNo();
        String content = "test1";

        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(content, userNo, postNo);

        Long commentNo = commentService.save(commentSaveDTO);

        assertThat(commentRepository.findById(commentNo)).isNotEmpty();
    }

    @Test
    public void updateTest() {
        Comment comment = commentRepository.findAll().get(0);
        Long commentNo = comment.getCommentNo();

        String content = "test1";

        CommentUpdateDTO commentUpdateDTO = new CommentUpdateDTO(commentNo, content);

        commentService.update(commentUpdateDTO);

        assertThat(commentRepository.findAll().get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void deleteTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        CommentDeleteDTO commentDeleteDTO = new CommentDeleteDTO(commentNo);

        commentService.delete(commentDeleteDTO);

        assertThat(commentRepository.findById(commentNo)).isEmpty();
    }

    @Test
    public void isLikedTest() {
        Comment comment = commentRepository.findAll().get(0);
        Long commentNo = comment.getCommentNo();
        User user = userRepository.findAll().get(0);

        CommentIsLikedDTO commentIsLikedDTO = new CommentIsLikedDTO(commentNo);

        assertThat(commentService.isLiked(commentIsLikedDTO)).isFalse();

        commentLikedRepository.save(
                CommentLiked.builder()
                        .comment(comment)
                        .user(user)
                        .build());

        assertThat(commentService.isLiked(commentIsLikedDTO)).isTrue();
    }
}
