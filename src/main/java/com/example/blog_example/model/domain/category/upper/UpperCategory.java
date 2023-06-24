package com.example.blog_example.model.domain.category.upper;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "upperCategory", orphanRemoval = true)
    private List<LowerCategory> lowerCategories;

    @OneToMany(mappedBy = "upperCategory", orphanRemoval = true)
    private List<Post> posts;

    @Builder
    public UpperCategory(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
