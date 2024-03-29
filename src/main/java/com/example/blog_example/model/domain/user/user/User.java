package com.example.blog_example.model.domain.user.user;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.comment.liked.CommentLiked;
import com.example.blog_example.model.domain.post.liked.PostLiked;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.util.TimeStamp;
import com.example.blog_example.util.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogVisitCount> blogVisitCountList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpperCategory> upperCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLiked> postLikedList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLiked> commentLikedList = new ArrayList<>();

    @Builder
    public User(String name, String blogName, String email, String password) {
        this.name = name;
        this.blogName = blogName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public void updateUserInfo(String name, String blogName) {
        this.name = name;
        this.blogName = blogName;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
