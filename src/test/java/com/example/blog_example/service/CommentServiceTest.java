package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.post.comment.Comment;
import com.example.blog_example.model.domain.post.comment.CommentRepository;
import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.model.domain.user.UserRepository;
import com.example.blog_example.model.dto.post.comment.*;
import com.example.blog_example.service.post.CommentService;
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

    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostDetailRepository postDetailRepository;
    @Autowired private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
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

        PostDetail postDetail = postDetailRepository.save(
                PostDetail.builder()
                        .title("test")
                        .content("test")
                        .post(post)
                        .build());

        postRepository.save(post);

        commentRepository.save(
                Comment.builder()
                        .content("test")
                        .user(user)
                        .postDetail(postDetail)
                        .build());
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        upperCategoryRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void findAllTest() {
        Integer size = commentRepository.findAll().size();

        assertThat(commentService.findAll().size()).isEqualTo(size);
    }

    @Test
    public void findTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        CommentFindDTO commentFindDTO = new CommentFindDTO(commentNo);

        assertThat(commentNo).isEqualTo(commentService.find(commentFindDTO).getCommentNo());
    }

    @Test
    public void findByObjectTest() {
        Long postDetailNo = postDetailRepository.findAll().get(0).getPostDetailNo();

        Long userNo = userRepository.findAll().get(0).getUserNo();

        CommentFindByObjectDTO commentFindByPostDetailDTO = new CommentFindByObjectDTO(postDetailNo);
        CommentFindByObjectDTO commentFindByUserDTO = new CommentFindByObjectDTO(userNo);

        assertThat(postDetailNo)
                .isEqualTo(commentService.findByPostDetail(commentFindByPostDetailDTO).get(0)
                        .getPostDetailVO().getPostDetailNo());
        assertThat(userNo)
                .isEqualTo(commentService.findByUser(commentFindByUserDTO).get(0).getUserVO().getUserNo());
    }

    @Test
    public void saveTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();
        Long postDetailNo = postDetailRepository.findAll().get(0).getPostDetailNo();

        CommentSaveDTO commentSaveDTO = new CommentSaveDTO("test", userNo, postDetailNo);

        Long commentNo = commentService.save(commentSaveDTO);

        assertThat(commentNo).isEqualTo(commentRepository.findAll().get(1).getCommentNo());
    }

    @Test
    public void updateTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();
        String content = "test1";

        CommentUpdateDTO commentUpdateDTO = new CommentUpdateDTO(commentNo, content);

        commentService.update(commentUpdateDTO);

        assertThat(content).isEqualTo(commentRepository.findAll().get(0).getContent());
    }

    @Test
    public void deleteTest() {
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        CommentDeleteDTO commentDeleteDTO = new CommentDeleteDTO(commentNo);

        commentService.delete(commentDeleteDTO);

        assertThat(commentRepository.findById(commentNo)).isEmpty();
    }
}
