package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.LoginRequest;
import org.example.donatebackend.dto.request.RegisterRequest;
import org.example.donatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody RegisterRequest req) {
        authService.registerUser(
                req.getUsername(),
                req.getEmail(),
                req.getPassword()
        );
        return Map.of("message", "Register success");
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        String token = authService.login(
                req.getUsername(),
                req.getPassword()
        );
        return Map.of("token", token);
    }
}