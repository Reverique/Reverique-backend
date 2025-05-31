package com.reverie_unique.reverique.infrastructure.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class DailyQuestionRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public DailyQuestionRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveQuestion(String key, Object question) {
        Duration ttl = getDurationUntilMidnight();
        redisTemplate.opsForValue().set(key, question, ttl);
    }

    public Object getQuestion(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private Duration getDurationUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.plusDays(1).toLocalDate().atStartOfDay();
        return Duration.between(now, midnight);
    }
}