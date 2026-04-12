package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.LoginRequest;
import org.example.donatebackend.dto.request.RegisterRequest;
import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.service.AuthService;
import org.example.donatebackend.service.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

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

    @GetMapping("/me")
    public Map<String, Object> me(@RequestHeader("Authorization") String  authHeader) {
        String token = authHeader.replace("Bearer ", "");

        String username = authService.extractUsername(token);

        UserEntity user = authService.getUserByUsername(username);

        return Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "avatar",user.getAvatar()
        );
    }

    @PostMapping("/google")
    public Map<String, String> google(@RequestBody Map<String,String> req) {
        try {
            String idToken = req.get("credential");

            var payload = googleTokenVerifier.verify(idToken);


            String email = payload.getEmail();
            String name = (String) payload.get("name");

            UserEntity userEntity = authService.findOrCreateGoogleUser(name,email);
            UserEntity.Role role = userEntity.getRole();

            String token = authService.createToken(userEntity.getUsername(),role);

            return Map.of("token", token);


        }catch (Exception e){
            return Map.of("error", "Invalid Google token");

        }

    }
}