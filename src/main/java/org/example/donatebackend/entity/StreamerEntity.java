package org.example.donatebackend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "streamers")
public class StreamerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String displayName;

    @Column(unique = true)
    private String donateToken;

    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
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
