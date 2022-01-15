package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_ID = 1;

    public static final Vote userVote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote adminVote1 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote userVote2 = new Vote(VOTE_ID + 2, LocalDate.of(2022, 1, 11));
    public static final Vote userVote3 = new Vote(VOTE_ID + 3, LocalDate.of(2022, 1, 10));
    public static final Vote adminVote2 = new Vote(VOTE_ID + 4, LocalDate.of(2022, 1, 11));

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }
}
