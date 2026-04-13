package org.example.donatebackend.service;

import org.example.donatebackend.dto.response.StreamerDetailReponse;
import org.example.donatebackend.dto.response.TopStreamerResponse;
import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.StreamerRepository;
import org.example.donatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public StreamerDetailReponse getByDonateToken(String donateToken){
        StreamerEntity streamer =  streamerRepository.findByDonateToken(donateToken);

        if(streamer == null){
            new Throwable("streamer not found");
        }
        StreamerDetailReponse streamerDetailReponse = new StreamerDetailReponse();
        streamerDetailReponse.setStreamerId(streamer.getId());
        streamerDetailReponse.setDisplayName(streamer.getDisplayName());
        streamerDetailReponse.setAvatar(streamer.getAvatar());
        streamerDetailReponse.setThumb(streamer.getThumb());
        streamerDetailReponse.setFollowers(streamer.getFollowers());

        return streamerDetailReponse;
    }
    public List<TopStreamerResponse> getTop10Streamer() {

        List<Object[]> res = streamerRepository.findTopStreamers(PageRequest.of(0, 10));

        return res.stream().map(r -> {
            TopStreamerResponse dto = new TopStreamerResponse();

            dto.setStreamerId((Long) r[0]);
            dto.setDisplayName((String) r[1]);
            dto.setTotalAmount(((Number) r[2]).doubleValue());

            return dto;
        }).toList();
    }
}


