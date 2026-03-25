package org.example.donatebackend.service;

import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.StreamerRepository;
import org.example.donatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class StreamerService {
    @Autowired
    private StreamerRepository streamerRepository;

    @Autowired
    private UserRepository userRepository;

    public StreamerEntity createStreamer(String displayName){
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));

        StreamerEntity s  = new StreamerEntity();

        s.setUserId(userEntity.getId());
        s.setDisplayName(displayName);
        s.setDonateToken(UUID.randomUUID().toString());

        return streamerRepository.save(s);
    }

    public StreamerEntity getByDonateToken(String donateToken){
        return streamerRepository.findByDonateToken(donateToken)
                .orElseThrow(() -> new RuntimeException("token not found"));
    }
}
