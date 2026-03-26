package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.StreamerRequest;
import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.entity.UserEntity;
import org.example.donatebackend.repository.UserRepository;
import org.example.donatebackend.service.StreamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/streamers")
public class StreamerController {

    @Autowired
    private StreamerService streamerService;

    @PostMapping("/create")
    public StreamerEntity createStreamer(@RequestBody StreamerRequest req) {

        return streamerService.createStreamer(req.getDisplayName());
    }

    @GetMapping("/donate/{token}")
    public StreamerEntity getByToken(@PathVariable String token) {
        return streamerService.getByDonateToken(token);
    }
}
