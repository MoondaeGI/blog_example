package com.example.blog_example.model.domain.post.file;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByPostDetail(PostDetail postDetail);
}
