package org.example.donatebackend.service;

import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.UserRepository;
import org.example.donatebackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.donatebackend.entity.UserEntity.Role;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public void registerUser(String username,String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setEmail(email);
        userEntity.setRole(Role.USER);

        userRepository.save(userEntity);
    }

    public String login(String username,String password) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if(!passwordEncoder.matches(password,userEntity.getPassword()))
        {
            throw new RuntimeException("invalid password");
        }

        return jwtUtil.generateToken(userEntity.getUsername(), userEntity.getRole());
    }
    public void updateRole(Long userId, Role role) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userEntity.setRole(role);
        userRepository.save(userEntity);
    }
}
