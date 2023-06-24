package com.example.blog_example.model.domain.post.comment;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_COMMENT")
public class Comment extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_NO")
    private Long commentNo;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO")
    private PostDetail postDetail;

    @Builder
    public Comment(String content, User user, PostDetail postDetail) {
        this.content = content;
        this.user = user;
        this.postDetail = postDetail;
    }

    public void update(String content) {
        this.content = content;
    }
}
