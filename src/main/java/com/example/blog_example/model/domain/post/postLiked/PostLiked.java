package com.example.blog_example.model.domain.post.postLiked;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_POST_LIKED")
public class PostLiked extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_LIKED_NO")
    private Long postLikedNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    @Builder
    public PostLiked(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
