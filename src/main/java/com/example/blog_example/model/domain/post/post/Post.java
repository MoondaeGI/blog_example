package com.example.blog_example.model.domain.post.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.post.postLiked.PostLiked;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.util.TimeStamp;
import com.example.blog_example.util.enums.OpenYN;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_POST")
public class Post extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NO", nullable = false)
    private Long postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CATEGORY_NO", nullable = false)
    private UpperCategory upperCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOWER_CATEGORY_NO", nullable = false)
    private LowerCategory lowerCategory;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "OPEN_YN", nullable = false)
    private OpenYN openYN;

    @Column(name = "VIEWS", nullable = false)
    private Integer views;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostLiked> postLikedList;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> commentList;

    @Builder
    public Post(User user, UpperCategory upperCategory, LowerCategory lowerCategory, String title, String content) {
        this.user = user;
        this.upperCategory = upperCategory;
        this.lowerCategory = lowerCategory;
        this.title = title;
        this.content = content;
        this.openYN = OpenYN.OPEN;
        this.views = 0;
    }

    public void update(UpperCategory upperCategory, LowerCategory lowerCategory, String title, String content) {
        this.upperCategory = upperCategory;
        this.lowerCategory = lowerCategory;
        this.title = title;
        this.content = content;
    }

    public Integer addViews() {
        return this.views += 1;
    }

    public OpenYN changeOpenYN() {
        return this.openYN = (this.openYN == OpenYN.OPEN) ? OpenYN.CLOSE : OpenYN.OPEN;
    }
}
