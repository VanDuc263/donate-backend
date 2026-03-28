package org.example.donatebackend.dto.response;

import org.example.donatebackend.entity.UserEntity;

public class AdminUserResponse {
    private Long id;
    private String username;
    private String email;
    private UserEntity.Role role;

    public AdminUserResponse() {
    }

    public AdminUserResponse(Long id, String username, String email, UserEntity.Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity.Role getRole() {
        return role;
    }

    public void setRole(UserEntity.Role role) {
        this.role = role;
    }
}
