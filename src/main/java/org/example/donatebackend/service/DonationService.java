package org.example.donatebackend.service;

import org.example.donatebackend.dto.request.DonationRequest;
import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.dto.response.TopDonorResponse;
import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.redis.RedisPublisher;
import org.example.donatebackend.repository.DonationRepository;
import org.example.donatebackend.repository.StreamerRepository;
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

    @Autowired
    private StreamerRepository streamerRepository;

    public DonationResponse saveDonation(DonationRequest req) {
        StreamerEntity streamer = streamerRepository.findById(req.getStreamerId()).orElseThrow(
                () -> new RuntimeException("Streamer not found")
        );

        Donation donation = new Donation();
        donation.setStreamer(streamer);
        donation.setDonorName(req.getDonorName());
        donation.setAmount(req.getAmount());
        donation.setMessage(req.getMessage());

        donationRepository.save(donation);

        DonationResponse response = new DonationResponse();
        response.setStreamerId(streamer.getId());
        response.setAmount(donation.getAmount());
        response.setDonorName(donation.getDonorName());
        response.setMessage(donation.getMessage());

        redisPublisher.publish(response);

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

    public List<Donation> findTop10ByOrderByCreatedAtDesc() {
        return donationRepository.findTop10ByOrderByCreatedAtDesc();

    }

    public List<DonationResponse> getLatestDonations(Long streamerId, int limit) {

        Pageable pageable = PageRequest.of(0, limit);

        List<Donation> donations =
                donationRepository.findByStreamer_IdOrderByCreatedAtDesc(streamerId, pageable);

        return donations.stream().map(d -> {
            DonationResponse res = new DonationResponse();
            res.setStreamerId(streamerId);
            res.setAmount(d.getAmount());
            res.setDonorName(d.getDonorName());
            res.setMessage(d.getMessage());
            return res;
        }).toList();
    }
}
