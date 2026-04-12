package org.example.donatebackend.redis;

import org.example.donatebackend.entity.Donation;
import org.example.donatebackend.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {

    @Autowired
    private WebSocketService  webSocketService;

    @EventListener
    public void handleMessage(Object message){
        Donation donation = (Donation)message;
//        webSocketService.sendDonateAlert(donation.getStreamerId(),donation);
    }
}
