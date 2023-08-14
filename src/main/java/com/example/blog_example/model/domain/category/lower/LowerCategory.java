package com.example.blog_example.model.domain.category.lower;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_LOWER_CATEGORY")
public class LowerCategory extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOWER_CATEGORY_NO", nullable = false)
    private Long lowerCategoryNo;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CATEGORY_NO", nullable = false)
    private UpperCategory upperCategory;

    @OneToMany(mappedBy = "lowerCategory")
    private List<Post> postList;

    @Builder
    public LowerCategory(String name, UpperCategory upperCategory) {
        this.name = name;
        this.upperCategory = upperCategory;
    }

    public void update(String name) {
        this.name = name;
    }
}
