package org.example.donatebackend.service;

import com.cloudinary.Cloudinary;
import org.example.donatebackend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserService userService;

    public String upload(String username,MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("folder", "avatars")
            );

            String url = (String) uploadResult.get("url");

            UserEntity user = userService.updateAvatar(username,url);
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
