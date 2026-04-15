package org.example.donatebackend.redis;

import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.service.WebSocketService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RedisSubscriber implements MessageListener {

    private final WebSocketService webSocketService;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisSubscriber(WebSocketService webSocketService, RedisTemplate<String, Object> redisTemplate) {
        this.webSocketService = webSocketService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            Object obj = redisTemplate.getValueSerializer().deserialize(message.getBody());

            DonationResponse donation = (DonationResponse) obj;

            webSocketService.sendDonateAlert(
                    donation.getStreamerId(),
                    donation
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}