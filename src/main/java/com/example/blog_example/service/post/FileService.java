package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.file.FileRepository;
import com.example.blog_example.model.dto.post.file.FileFindByPostDTO;
import com.example.blog_example.model.dto.post.file.FileFindDTO;
import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.vo.post.FileVO;
import com.example.blog_example.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final PostDetailRepository postDetailRepository;
    private final FileHandler fileHandler;

    @Transactional(readOnly = true)
    public FileVO find(FileFindDTO fileFindDTO) {
        return FileVO.from(
                fileRepository.findById(fileFindDTO.getFileNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<FileVO> findByPostDetailNo(FileFindByPostDTO fileFindByPostDTO) {
        PostDetail postDetail =
                postDetailRepository.findById(fileFindByPostDTO.getPostDetailNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return fileRepository.findByPostDetail(postDetail).stream()
                .map(FileVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(FileSaveDTO fileSaveDTO) {
        PostDetail postDetail =
                postDetailRepository.findById(fileSaveDTO.getPostDetailNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        for(MultipartFile multipartFile : fileSaveDTO.getMultipartFiles()) {
            fileHandler.uploadFile(postDetail, multipartFile);
        }
    }

    @Transactional
    public void update() {}

    @Transactional
    public void delete() {}
}
