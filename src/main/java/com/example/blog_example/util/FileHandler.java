package com.example.blog_example.util;

import com.example.blog_example.model.domain.post.file.File;
import com.example.blog_example.model.domain.post.post.Post;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class FileHandler {
    public void parseMultipartFile(Post post, MultipartFile multipartFile) {

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

        String originalFileName = multipartFile.getOriginalFilename();

        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");

        String uploadedFileName = uuids[0] + originalFileName;

        folder = new java.io.File(absolutePath + path + "/" + uploadedFileName);
        try {
            multipartFile.transferTo(folder);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        File.builder()
                .name(uploadedFileName)
                .originalFileName(originalFileName)
                .path(path + "/" + uploadedFileName)
                .size(multipartFile.getSize())
                .post(post)
                .build();
    }

    public byte[] parseImageFile(File file) {
        byte[] imageFile = new byte[0];

        try {
            InputStream inputStream = Files.newInputStream(Paths.get(file.getPath()));
            imageFile = IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    public Boolean deleteFile(File file) {
        java.io.File deleteFile = new java.io.File(file.getPath());

        if (deleteFile.exists()) {
            try {
                deleteFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
