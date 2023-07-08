package com.example.blog_example.model.domain.post.liked;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostLikedRepository extends JpaRepository<PostLiked, Long> {
    PostLiked findByPost(Post post);
    Long countByPost(Post post);
    void deleteByPost(Post post);
    Boolean existsByPost(Post post);
    @Query("SELECT post " +
            "FROM Post post " +
            "WHERE post IN (SELECT postLiked.post " +
            "               FROM PostLiked  postLiked " +
            "               WHERE postLiked.user = :user)")
    List<Post> findPostByUser(User user);
}
