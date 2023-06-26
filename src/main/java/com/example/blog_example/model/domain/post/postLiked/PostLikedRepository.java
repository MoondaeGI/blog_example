package com.example.blog_example.model.domain.post.postLiked;

import com.example.blog_example.model.domain.post.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikedRepository extends JpaRepository<PostLiked, Long> {
    PostLiked findByPost(Post post);
    Long countByPost(Post post);
    void deleteByPost(Post post);
}
