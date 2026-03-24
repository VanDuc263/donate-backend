package org.example.donatebackend.controller;

import org.example.donatebackend.repository.UserRepository;
import org.example.donatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void registerUser(@RequestBody Map<String,String> req) {
        authService.registerUser(
                req.get("username"),
                req.get("email"),
                req.get("password")
        );

    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> req) {
        return authService.login(
                req.get("username"),
                req.get("password")
        );
    }

}
