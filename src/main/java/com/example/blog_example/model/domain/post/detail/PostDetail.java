package com.example.blog_example.model.domain.post.detail;

import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.util.enums.OpenYN;
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
    @Id
    @Column(name = "POST_NO")
    private Long postDetailNo;

    @OneToOne
    @MapsId @JoinColumn(name = "POST_NO")
    private Post post;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "OPEN_YN")
    private OpenYN openYN;

    @Column(name = "VIEWS")
    private Integer views;

    @OneToMany(mappedBy = "postDetail", orphanRemoval = true)
    private List<File> files;

    @Builder
    public PostDetail(Post post, String title, String content) {
        this.post = post;
        this.title = title;
        this.content = content;
        this.openYN = OpenYN.OPEN;
        this.views = 0;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Integer addViews() {
        return this.views += 1;
    }

    public OpenYN changeOpenYN() {
        this.openYN = (this.openYN == OpenYN.OPEN) ? OpenYN.CLOSE : OpenYN.OPEN;

        return this.openYN;
    }
}
