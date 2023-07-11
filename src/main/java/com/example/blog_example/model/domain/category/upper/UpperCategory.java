package com.example.blog_example.model.domain.category.upper;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_UPPER_CATEGORY")
public class UpperCategory extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UPPER_CATEGORY_NO", nullable = false)
    private Long upperCategoryNo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    @OneToMany(mappedBy = "upperCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LowerCategory> lowerCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "upperCategory")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public UpperCategory(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void update(String name) {
        this.name = name;
    }
}
