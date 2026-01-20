package com.example.university.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public <T> Optional<T> getData(String key, Class<T> type){
        Object data = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(data).map(type::cast);
    }

    public void setData(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }
}
