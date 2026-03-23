package org.example.donatebackend.controller;

import org.example.donatebackend.repository.UserRepository;
import org.example.donatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
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

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> req) {
        return authService.login(
                req.get("username"),
                req.get("password")
        );
    }

}
