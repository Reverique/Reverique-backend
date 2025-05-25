package com.reverie_unique.reverique.common.emial;

import com.reverie_unique.reverique.common.email.Strategy.EmailSenderStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailSenderContext {

    private final Map<String, EmailSenderStrategy> strategyMap;

    public EmailSenderContext(Map<String, EmailSenderStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public void sendEmail(String strategyKey, String to, String subject, String body) {
        EmailSenderStrategy strategy = strategyMap.get(strategyKey);
        if (strategy == null) {
            throw new IllegalArgumentException("No email sender strategy found for key: " + strategyKey);
        }
        strategy.send(to, subject, body);
    }
}