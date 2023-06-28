package com.example.blog_example.model.domain.comment.liked;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_COMMENT_LIKED")
public class CommentLiked extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_LIKED_NO")
    private Long commentLikedNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_NO")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @Builder
    public CommentLiked(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
