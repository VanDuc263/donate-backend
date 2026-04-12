package org.example.donatebackend.service;

import org.example.donatebackend.dto.request.DonationRequest;
import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.redis.RedisPublisher;
import org.example.donatebackend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private RedisPublisher redisPublisher;

    public Donation donation(Donation donation){
        Donation saved =  donationRepository.save(donation);

        redisPublisher.publish(saved);
        return saved;
    }

    public DonationResponse saveDonation(DonationRequest req) {
        Donation donation = new Donation();
        donation.setDonorName(req.getDonorName());
        donation.setAmount(req.getAmount());
        donation.setMessage(req.getMessage());

        donationRepository.save(donation);

        DonationResponse response = new DonationResponse();
        response.setAmount(donation.getAmount());
        response.setDonorName(donation.getDonorName());
        response.setMessage(donation.getMessage());

        return response;
    }

    public List<Donation> findStreamerId(Long streamerId){
        return donationRepository.findByStreamerId(streamerId);
    }
}
