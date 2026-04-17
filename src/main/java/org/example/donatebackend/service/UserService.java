package org.example.donatebackend.service;

import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadService fileUploadService;

    public UserEntity updateAvatar(String username,String url){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found")
        );

        user.setAvatar(url);

        return userRepository.save(user);

    }

    public String uploadUserAvatar(String username, MultipartFile file) {
        String url = fileUploadService.upload("USER", file);
        updateAvatar(username,url);
        return url;

    }
}
