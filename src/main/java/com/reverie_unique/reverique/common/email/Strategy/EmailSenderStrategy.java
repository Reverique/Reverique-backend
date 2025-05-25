package com.reverie_unique.reverique.common.email.Strategy;

public interface EmailSenderStrategy {
    void send(String to, String subject, String body);
}