package org.example.donatebackend.service;

import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> findStreamerId(Long streamerId){
        return donationRepository.findByStreamerId(streamerId);
    }
}
