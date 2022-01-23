package com.github.banido_pixel.restaurant_voting.web;

import com.github.banido_pixel.restaurant_voting.web.vote.VoteTestData;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class FixedLegalClockConfig {
    @Primary
    @Bean
    Clock fixedClock() {
        return Clock.fixed(
                Instant.parse(VoteTestData.LEGAL_TIME),
                ZoneId.of("UTC"));
    }
}
