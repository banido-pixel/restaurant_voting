package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String LEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 5, 1)) + "Z";
    public static final String ILLEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 5, 1)) + "Z";

    public static final Vote userVote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote adminVote1 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote userVote2 = new Vote(VOTE_ID + 2, LocalDate.of(2022, 1, 11));
    public static final Vote userVote3 = new Vote(VOTE_ID + 3, LocalDate.of(2022, 1, 10));
    public static final Vote adminVote2 = new Vote(VOTE_ID + 4, LocalDate.of(2022, 1, 11));

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now());
    }
}
