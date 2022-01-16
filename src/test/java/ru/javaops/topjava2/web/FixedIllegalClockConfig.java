package ru.javaops.topjava2.web;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static ru.javaops.topjava2.web.vote.VoteTestData.ILLEGAL_TIME;

@TestConfiguration
public class FixedIllegalClockConfig {
    @Primary
    @Bean
    Clock fixedClock() {
        return Clock.fixed(
                Instant.parse(ILLEGAL_TIME),
                ZoneId.of("UTC"));
    }
}
