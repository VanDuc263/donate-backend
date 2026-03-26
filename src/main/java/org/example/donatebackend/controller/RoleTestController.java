package org.example.donatebackend.controller;

import org.example.donatebackend.entity.UserEntity.Role;
import org.example.donatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleTestController {

    @Autowired
    private AuthService authService;

    @GetMapping("/api/user/me")
    public String userEndpoint(Authentication authentication) {
        return "Hello USER area, current user: " + authentication.getName();
    }

    @GetMapping("/api/streamer/dashboard")
    public String streamerEndpoint(Authentication authentication) {
        return "Hello STREAMER area, current user: " + authentication.getName();
    }

    @GetMapping("/api/admin/dashboard")
    public String adminEndpoint(Authentication authentication) {
        return "Hello ADMIN area, current user: " + authentication.getName();
    }

    @PutMapping("/api/admin/users/{userId}/role")
    public String updateRole(@PathVariable Long userId, @RequestParam Role role) {
        authService.updateRole(userId, role);
        return "Updated role successfully";
    }
}