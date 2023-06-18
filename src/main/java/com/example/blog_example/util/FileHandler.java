package com.example.blog_example.util;

import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.file.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class FileHandler {
    public File uploadFile(PostDetail postDetail, MultipartFile multipartFile) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = simpleDateFormat.format(new Date());

        String path = "upload/" + currentDate;
        String absolutePath = new java.io.File("").getAbsolutePath() + "\\";

        java.io.File folder = new java.io.File(path);
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        String contentType = multipartFile.getContentType();
        String originalFileName = multipartFile.getOriginalFilename();

        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");

        String uploadedFileName = uuids[0] + originalFileName;

        File file = File.builder()
                .name(uploadedFileName)
                .originalFileName(originalFileName)
                .size(multipartFile.getSize())
                .postDetail(postDetail)
                .build();

        folder = new java.io.File(absolutePath + path + "/" + uploadedFileName);
        try {
            multipartFile.transferTo(folder);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public void downloadFile() {}

    public void deleteFile(File file) {}
}
