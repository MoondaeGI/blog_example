package com.example.blog_example.model.domain.post.post;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.comment.Comment;
import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.model.domain.util.TimeStamp;
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
    @Column(name = "POST_NO")
    private Long postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CATEGORY_NO")
    private UpperCategory upperCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOWER_CATEGORY_NO")
    private LowerCategory lowerCategory;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "POST_DETAIL_NO")
    private PostDetail postDetail;

    @Builder
    public Post(User user, UpperCategory upperCategory, LowerCategory lowerCategory) {
        this.user = user;
        this.upperCategory = upperCategory;
        this.lowerCategory = lowerCategory;
    }
}
