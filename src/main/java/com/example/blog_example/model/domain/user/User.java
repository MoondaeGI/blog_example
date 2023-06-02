package com.example.blog_example.model.domain.user;

import com.example.blog_example.model.domain.comment.Comment;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Long userNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BLOG_NAME")
    private String blogName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    public User(String name, String blogName) {
        this.name = name;
        this.blogName = blogName;
    }
}
