package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.file.FileRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
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
    private final PostRepository postRepository;
    private final FileHandler fileHandler;

    @Transactional(readOnly = true)
    public FileVO find(FileFindDTO fileFindDTO) {
        return FileVO.from(
                fileRepository.findById(fileFindDTO.getFileNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<FileVO> findAll(FileFindAllDTO fileFindAllDTO) {
        Post post = postRepository.findById(fileFindAllDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return fileRepository.findByPost(post).stream()
                .map(FileVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(FileSaveDTO fileSaveDTO) {
        Post post = postRepository.findById(fileSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        for(MultipartFile multipartFile : fileSaveDTO.getMultipartFiles()) {
            fileHandler.parseMultipartFile(post, multipartFile);
        }
    }

    @Transactional
    public void update(FileUpdateDTO fileUpdateDTO) {
        Post post = postRepository.findById(fileUpdateDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        List<File> files = fileRepository.findByPost(post);

        List<String> multipartFileNames = new ArrayList<>();
        for (MultipartFile multipartFile : fileUpdateDTO.getMultipartFiles()) {
            multipartFileNames.add(multipartFile.getName());
        }

        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            fileNames.add(file.getOriginalFileName());
        }

        for (MultipartFile multipartFile : fileUpdateDTO.getMultipartFiles()) {
            String multipartFileName = multipartFile.getOriginalFilename();
            if (!fileNames.contains(multipartFileName)) {
                fileHandler.parseMultipartFile(post, multipartFile);
            }
        }

        for (File file : files) {
            String fileName = file.getOriginalFileName();
            if (!multipartFileNames.contains(fileName)) {
                fileHandler.deleteFile(file);
            }
        }
    }

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
        Post post = postRepository.findById(fileDeleteAllDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        List<File> files = fileRepository.findByPost(post);

        for (File file : files) {
            fileHandler.deleteFile(file);
        }

        fileRepository.deleteByPost(post);
    }
}
