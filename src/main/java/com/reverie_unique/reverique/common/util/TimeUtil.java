package com.reverie_unique.reverique.common.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {

    public static Duration getDurationUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.plusDays(1).toLocalDate().atStartOfDay();
        return Duration.between(now, midnight);
    }
}