package com.example.blog_example.model.domain.post.detail;

import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_POST_DETAIL")
public class PostDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NO")
    private Long postDetailNo;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VIEWS")
    private Integer views;

    @OneToOne(mappedBy = "postDetail", orphanRemoval = true)
    private Post post;

    @OneToMany(mappedBy = "postDetail", orphanRemoval = true)
    private List<File> files;

    @Builder
    public PostDetail(String title, String content) {
        this.title = title;
        this.content = content;
        this.views = 0;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Integer addViews() {
        this.views += 1;

        return views;
    }
}
