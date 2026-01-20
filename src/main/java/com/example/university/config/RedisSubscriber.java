package com.example.university.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            // 1. redis에서 발행된 메시지를 읽음
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            log.info("Redis에서 받은 메시지: {}", publishMessage);

            // 2. webSocket 구독자들에게 메시지 전달 (/sub/chat 채널)
            messagingTemplate.convertAndSend("/sub/chat", publishMessage);
        }catch (Exception e){
            log.error("메세지 처리 중 에러 발생 : {}", e.getMessage());
        }
    }
}
