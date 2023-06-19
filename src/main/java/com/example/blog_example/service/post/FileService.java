package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.file.FileRepository;
import com.example.blog_example.model.dto.post.file.*;
import com.example.blog_example.model.vo.post.FileVO;
import com.example.blog_example.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public List<FileVO> findAll(FileFindAllDTO fileFindAllDTO) {
        PostDetail postDetail =
                postDetailRepository.findById(fileFindAllDTO.getPostDetailNo())
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
            fileHandler.parseMultipartFile(postDetail, multipartFile);
        }
    }

    @Transactional
    public void update(FileUpdateDTO fileUpdateDTO) {}

    @Transactional
    public void delete(FileDeleteDTO fileDeleteDTO) {
        File file = fileRepository.findById(fileDeleteDTO.getFileNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));

        if (fileHandler.deleteFile(file)) {
            fileRepository.delete(file);
        }
    }

    @Transactional
    public void deleteAll(FileDeleteAllDTO fileDeleteAllDTO) {
        PostDetail postDetail = postDetailRepository.findById(fileDeleteAllDTO.getPostDetailNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        List<File> files = fileRepository.findByPostDetail(postDetail);

        for (File file : files) {
            fileHandler.deleteFile(file);
        }

        fileRepository.deleteByPostDetail(postDetail);
    }
}
