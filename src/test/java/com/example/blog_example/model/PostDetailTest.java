package com.example.blog_example.model;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostDetailTest {
    @Autowired private PostRepository postRepository;
    @Autowired private PostDetailRepository postDetailRepository;

    @Test
    public void postDetailDataTest() {
        UpperCategory upperCategory = UpperCategory.builder()
                .name("test")
                .build();

        LowerCategory lowerCategory = LowerCategory.builder()
                .name("test")
                .upperCategory(upperCategory)
                .build();

        User user = User.builder()
                .name("test")
                .blogName("test")
                .build();

        Post post = Post.builder()
                .upperCategory(upperCategory)
                .lowerCategory(lowerCategory)
                .user(user)
                .build();

        PostDetail postDetail = PostDetail.builder()
                .title("test")
                .content("test")
                .post(post)
                .build();

        Long postNo = postRepository.save(post).getPostNo();
        postDetailRepository.save(postDetail);

        assertThat(postDetail.getPostDetailNo()).isEqualTo(postNo);
    }
}
