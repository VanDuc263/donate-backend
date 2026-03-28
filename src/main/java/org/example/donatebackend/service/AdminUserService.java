package org.example.donatebackend.service;

import org.example.donatebackend.dto.request.AdminCreateUserRequest;
import org.example.donatebackend.dto.request.AdminUpdateUserRequest;
import org.example.donatebackend.dto.response.AdminUserResponse;
import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.StreamerRepository;
import org.example.donatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreamerRepository streamerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AdminUserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toResponse(user);
    }

    public AdminUserResponse createUser(AdminCreateUserRequest req) {
        if (req.getUsername() == null || req.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (req.getPassword() == null || req.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        if (req.getRole() == null) {
            throw new RuntimeException("Role is required");
        }

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.getUsername().trim());
        user.setEmail(req.getEmail().trim());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        userRepository.save(user);

        return toResponse(user);
    }

    public AdminUserResponse updateUser(Long id, AdminUpdateUserRequest req) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getUsername() != null && !req.getUsername().trim().isEmpty()) {
            userRepository.findByUsername(req.getUsername().trim())
                    .ifPresent(existingUser -> {
                        if (existingUser.getId() != user.getId()) {
                            throw new RuntimeException("Username already exists");
                        }
                    });
            user.setUsername(req.getUsername().trim());
        }

        if (req.getEmail() != null && !req.getEmail().trim().isEmpty()) {
            userRepository.findByEmail(req.getEmail().trim())
                    .ifPresent(existingUser -> {
                        if (existingUser.getId() != user.getId()) {
                            throw new RuntimeException("Email already exists");
                        }
                    });
            user.setEmail(req.getEmail().trim());
        }

        if (req.getPassword() != null && !req.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        if (req.getRole() != null) {
            user.setRole(req.getRole());
        }

        userRepository.save(user);

        return toResponse(user);
    }

    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        streamerRepository.findByUserId(id).ifPresent(streamer -> {
            streamerRepository.deleteByUserId(id);
        });

        userRepository.delete(user);
    }

    private AdminUserResponse toResponse(UserEntity user) {
        return new AdminUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}