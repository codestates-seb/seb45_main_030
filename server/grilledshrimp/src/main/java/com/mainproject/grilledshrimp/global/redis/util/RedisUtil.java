package com.mainproject.grilledshrimp.global.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> redisBlackListTemplate;

    public void set(String key, Object o, int minutes) {
        redisTemplate.opsForValue().set(key, o);
        redisTemplate.expire(key, minutes, java.util.concurrent.TimeUnit.MINUTES);
    }
}
