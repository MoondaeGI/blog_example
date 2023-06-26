package com.example.blog_example.model.domain.post.file;

import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.util.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_FILE")
public class File extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_NO", nullable = false)
    private Long fileNo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ORIGINAL_FILE_NAME", nullable = false)
    private String originalFileName;

    @Column(name = "PATH", nullable = false)
    private String path;

    @Column(name = "SIZE", nullable = false)
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO", nullable = false)
    private Post post;

    @Builder
    public File(String name, String originalFileName, String path, Long size, Post post) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.path = path;
        this.size = size;
        this.post = post;
    }
}
