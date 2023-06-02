package com.example.blog_example.model.domain.category.lower;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.util.TimeStamp;
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
    @Column(name = "LOWER_CATEGORY_NO")
    private Long lowerCategoryNo;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CATEGORY_NO")
    private UpperCategory upperCategory;

    @OneToMany(mappedBy = "lowerCategory")
    private List<Post> posts;

    @Builder
    public LowerCategory(String name, UpperCategory upperCategory) {
        this.name = name;
        this.upperCategory = upperCategory;
    }
}
