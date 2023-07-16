package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.file.FileRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.dto.post.file.FileSaveDTO;
import com.example.blog_example.model.dto.post.file.FileUpdateDTO;
import com.example.blog_example.model.vo.post.FileVO;
import com.example.blog_example.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private final FileHandler fileHandler;

    @Transactional(readOnly = true)
    public FileVO find(Long fileNo) {
        return FileVO.from(fileRepository.findById(fileNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<FileVO> findByPost(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return fileRepository.findByPost(post).stream()
                .map(FileVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(FileSaveDTO fileSaveDTO) {
        Post post = postRepository.findById(fileSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        for (MultipartFile multipartFile : fileSaveDTO.getMultipartFiles()) {
            fileRepository.save(fileHandler.parseMultipartFile(post, multipartFile));
        }
    }

    @Transactional
    public void update(FileUpdateDTO fileUpdateDTO) {
        Post post = postRepository.findById(fileUpdateDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        List<String> updateFileNameList = fileUpdateDTO.getMultipartFiles().stream()
                .map(MultipartFile::getName)
                .collect(Collectors.toList());

        List<File> uploadedFileList = fileRepository.findByPost(post);
        List<String> uploadedFileName = uploadedFileList.stream()
                .map(File::getOriginalFileName)
                .collect(Collectors.toList());

        for (MultipartFile updateFile : fileUpdateDTO.getMultipartFiles()) {
            String updateFileName = updateFile.getOriginalFilename();
            if (!uploadedFileName.contains(updateFileName)) {
                fileRepository.save(fileHandler.parseMultipartFile(post, updateFile));
            }
        }

        for (File uploadedFile : uploadedFileList) {
            String fileName = uploadedFile.getOriginalFileName();
            if (!updateFileNameList.contains(fileName)) {
                if (fileHandler.deleteFile(uploadedFile)) fileRepository.delete(uploadedFile);
            }
        }
    }

    @Transactional
    public void delete(Long fileNo) {
        File file = fileRepository.findById(fileNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));

        if (fileHandler.deleteFile(file)) {
            fileRepository.delete(file);
        }
    }

    @Transactional
    public void deleteByPost(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<File> files = fileRepository.findByPost(post);

        for (File file : files) {
            fileHandler.deleteFile(file);
        }

        fileRepository.deleteByPost(post);
    }

    @Transactional
    public Boolean isExist(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return fileRepository.existsByPost(post);
    }
}
