package org.example.donatebackend.controller;

import org.example.donatebackend.dto.request.DonationRequest;
import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.dto.response.TopDonorResponse;
import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donate")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @PostMapping
    public DonationResponse saveDonation(@RequestBody DonationRequest req) {
        return donationService.saveDonation(req);
    }

    @GetMapping("/{token}")
    public List<TopDonorResponse> getTopDonorByStreamer(@PathVariable String token) {
        return donationService.findTopDonors(token);
    }


}
