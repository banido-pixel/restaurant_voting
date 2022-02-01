package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String VOTE_DATE = LocalDate.of(2022, 1, 11).toString();
    public static final String VOTE_DATE_2 = LocalDate.of(2022, 1, 10).toString();
    public static final String LEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 5, 1)) + "Z";
    public static final String ILLEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 5, 1)) + "Z";

    public static final Vote userVote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote adminVote1 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote userVote2 = new Vote(VOTE_ID + 2, LocalDate.parse(VOTE_DATE));
    public static final Vote userVote3 = new Vote(VOTE_ID + 3, LocalDate.parse(VOTE_DATE_2));
    public static final Vote adminVote2 = new Vote(VOTE_ID + 4, LocalDate.parse(VOTE_DATE));

    public static final List<Vote> userVotes = Stream.of(userVote1,userVote2,userVote3)
            .sorted(Comparator.comparing(Vote::getDate)).toList();

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now());
    }
}
