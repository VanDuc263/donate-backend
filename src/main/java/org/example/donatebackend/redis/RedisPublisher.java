package org.example.donatebackend.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {

    @Autowired
    private RedisTemplate<String,Object>  redisTemplate;


    public void publish(Object message){
        redisTemplate.convertAndSend("donate-channel",message);
    }
}
