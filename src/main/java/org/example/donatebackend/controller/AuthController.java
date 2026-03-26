package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.LoginRequest;
import org.example.donatebackend.dto.request.RegisterRequest;
import org.example.donatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest req) {
        authService.registerUser(
                req.getUsername(),
                req.getEmail(),
                req.getPassword()
        );

    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return authService.login(
                req.getUsername(),
                req.getPassword()
        );
    }

}
