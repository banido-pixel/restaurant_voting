package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "date");

    public static final int VOTE_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String VOTE_DATE = LocalDate.of(2022, 1, 11).toString();
    public static final String VOTE_DATE_2 = LocalDate.of(2022, 1, 10).toString();
    public static final String LEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 5, 1)) + "Z";
    public static final String ILLEGAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 5, 1)) + "Z";

    public static final VoteTo userVote1 = new VoteTo(VOTE_ID, LocalDate.now(), 1);
    public static final VoteTo adminVote1 = new VoteTo(VOTE_ID + 1, LocalDate.now(), 3);
    public static final VoteTo userVote2 = new VoteTo(VOTE_ID + 2, LocalDate.parse(VOTE_DATE), 1);
    public static final VoteTo userVote3 = new VoteTo(VOTE_ID + 3, LocalDate.parse(VOTE_DATE_2), 2);
    public static final VoteTo adminVote2 = new VoteTo(VOTE_ID + 4, LocalDate.parse(VOTE_DATE), 1);

    public static final List<VoteTo> userVotes = Stream.of(userVote1, userVote2, userVote3)
            .sorted(Comparator.comparing(VoteTo::getDate)).toList();

    public static VoteTo getNew() {
        return new VoteTo(null, LocalDate.now(), 1);
    }

    public static VoteTo getUpdated() {
        return new VoteTo(VOTE_ID, LocalDate.now(), 1);
    }
}
