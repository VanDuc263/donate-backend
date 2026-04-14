package org.example.donatebackend.service;

import org.example.donatebackend.dto.request.DonationRequest;
import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.dto.response.TopDonorResponse;
import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.redis.RedisPublisher;
import org.example.donatebackend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<TopDonorResponse> findTopDonors(String token){
        List<Object[]> objects =  donationRepository.findTopDonors(token, PageRequest.of(0, 10));

        return objects.stream().map(o -> {
            TopDonorResponse donation = new TopDonorResponse();

            donation.setDonorName((String) o[0]);
            donation.setTotalAmount((Double) o[1]);

            return donation;
        }).toList();
    }
}
