package org.example.donatebackend.controller;

import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donate")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @PostMapping
    public Donation saveDonation(@RequestBody Donation donation) {
        return donationService.saveDonation(donation);
    }

    @GetMapping("/{streamerId}")
    public List<Donation> getByStreamer(@PathVariable Long streamerId) {
        return donationService.findStreamerId(streamerId);
    }
}
