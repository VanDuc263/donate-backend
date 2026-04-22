package org.example.donatebackend.service;

import org.example.donatebackend.dto.request.DonationRequest;
import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.dto.response.TopDonorResponse;
import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.entity.NotificationEntity;
import org.example.donatebackend.entity.StreamerEntity;
import org.example.donatebackend.redis.RedisPublisher;
import org.example.donatebackend.repository.DonationRepository;
import org.example.donatebackend.repository.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private StreamerRepository streamerRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private NotificationService notificationService;

    public DonationResponse saveDonation(DonationRequest req) {
        StreamerEntity streamer = streamerRepository.findById(req.getStreamerId()).orElseThrow(
                () -> new RuntimeException("Streamer not found")
        );

        Donation donation = new Donation();
        donation.setStreamer(streamer);
        donation.setDonorName(req.getDonorName());
        donation.setAmount(req.getAmount());
        donation.setMessage(req.getMessage());
        donation.setCreatedAt(LocalDateTime.now());
        donation.setDonorId(req.getDonorId());

        Donation savedDonation = donationRepository.save(donation);

        String key = "ranking:streamer:" + streamer.getToken();

        redisTemplate.opsForZSet().incrementScore(
                key,
                donation.getDonorName(),
                donation.getAmount()
        );

        Set<ZSetOperations.TypedTuple<Object>> result = redisTemplate.opsForZSet().reverseRangeWithScores(
                key,0,9
        );

        List<TopDonorResponse> topDonorResponses = result.stream().map(
                item -> {
                    TopDonorResponse topDonorResponse = new TopDonorResponse();
                    topDonorResponse.setDonorName((String)item.getValue());
                    topDonorResponse.setTotalAmount(item.getScore());
                    return  topDonorResponse;
                }
        ).toList();

        DonationResponse response = new DonationResponse();
        response.setStreamerId(streamer.getId());
        response.setAmount(donation.getAmount());
        response.setDonorName(donation.getDonorName());
        response.setMessage(donation.getMessage());
        response.setTopDonors(topDonorResponses);

        // 1) thông báo cho người donate, nếu có tài khoản đăng nhập
        if (req.getDonorId() != null) {
            notificationService.createNotification(
                    req.getDonorId(),
                    NotificationEntity.NotificationType.DONATION,
                    "Donate thành công",
                    "Bạn đã donate " + savedDonation.getAmount() + "đ cho streamer " + streamer.getDisplayName(),
                    "/account/donations",
                    "{\"amount\":" + savedDonation.getAmount() + "}"
            );
        }

        // 2) thông báo cho streamer nhận donate
        if (streamer.getUserId() != null) {
            notificationService.createNotification(
                    streamer.getUserId(),
                    NotificationEntity.NotificationType.DONATION,
                    "Bạn vừa nhận được donate mới",
                    savedDonation.getDonorName() + " vừa donate " + savedDonation.getAmount() + "đ cho bạn",
                    "/account/donations",
                    "{\"amount\":" + savedDonation.getAmount() + "}"
            );
        }

        redisPublisher.publish(response);

        return response;
    }

    public List<TopDonorResponse> findTopDonors(String token){
        List<Object[]> objects =  donationRepository.findTopDonors(token, PageRequest.of(0, 10));

        System.out.println("object"+objects);

        return objects.stream().map(o -> {
            TopDonorResponse donation = new TopDonorResponse();

            donation.setDonorName((String) o[0]);
            donation.setTotalAmount((Double) o[1]);

            return donation;
        }).toList();
    }

    public List<TopDonorResponse> findTopDonorsRedis(String token){
        String key = "ranking:streamer:" + token;

        Set<ZSetOperations.TypedTuple<Object>> results =
                redisTemplate.opsForZSet()
                        .reverseRangeWithScores(key, 0, 9);

        if(results == null || results.isEmpty()){
            List<TopDonorResponse> topDonorResponses =  findTopDonors(token);

            System.out.println("top donor"+topDonorResponses);

            for(TopDonorResponse o : topDonorResponses){
                String donorName = o.getDonorName();
                Double totalAmount = o.getTotalAmount();

                redisTemplate.opsForZSet().add(key, donorName, totalAmount);


            }
            results = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 9);
            System.out.println("results: " + results);
        }
        return results.stream().map(item -> {
            TopDonorResponse res = new TopDonorResponse();
            res.setDonorName((String) item.getValue());
            res.setTotalAmount(item.getScore());
            return res;
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
