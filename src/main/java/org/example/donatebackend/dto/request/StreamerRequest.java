package org.example.donatebackend.dto.request;

import jakarta.persistence.Column;

import java.util.Date;

public class StreamerRequest {
    private Long userId;
    private String displayName;
    private String donateToken;
    private Date createdAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDonateToken() {
        return donateToken;
    }

    public void setDonateToken(String donateToken) {
        this.donateToken = donateToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
