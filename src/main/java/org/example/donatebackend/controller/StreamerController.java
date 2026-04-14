package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.StreamerRequest;
import org.example.donatebackend.dto.response.StreamerDetailReponse;
import org.example.donatebackend.dto.response.TopStreamerResponse;
import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.service.StreamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streamers")
public class StreamerController {

    @Autowired
    private StreamerService streamerService;

    @PostMapping("/create")
    public StreamerEntity createStreamer(@RequestBody StreamerRequest req) {

        return streamerService.createStreamer(req.getDisplayName());
    }

    @GetMapping("/{token}")
    public StreamerDetailReponse getByToken(@PathVariable String token) {
        System.out.println(token);
        return streamerService.getByDonateToken(token);
    }

    @GetMapping("/top")
    public List<TopStreamerResponse> getTopStreamers() {
        return streamerService.getTop10Streamer();
    }

}
