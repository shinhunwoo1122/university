package com.example.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/api/publish")
    public ResponseEntity<String> publish(@RequestParam String message){
        //"chat" 채널로 메시지 발행
        redisTemplate.convertAndSend("chat", message);
        return ResponseEntity.ok("발행 완료: " + message);
    }

}
