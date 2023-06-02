package com.example.blog_example.model.domain.post.file;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.util.TimeStamp;
import com.example.blog_example.model.domain.util.enums.FileType;
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
    @Column(name = "FILE_NO")
    private Long fileNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "TYPE")
    private FileType type;

    @Column(name = "SIZE")
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO")
    private PostDetail postDetail;

    @Builder
    public File(String name, String originalFileName, FileType type, Long size, PostDetail postDetail) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.type = type;
        this.size = size;
        this.postDetail = postDetail;
    }
}
