package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.SecurityUtil;

import java.time.LocalDate;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@Slf4j
public class AbstractVoteController {

    @Autowired
    private VoteService voteService;

    public void delete(int id, int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("delete vote {} for user {} for restaurant with id = {}", id, userId, restaurantId);
        voteService.delete(id, userId);
    }

    public void update(Vote vote, int id, int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("update vote {} for user {} for restaurant {}", vote, userId, restaurantId);
        assureIdConsistent(vote, id);
        Assert.notNull(vote, "vote must not be null");
        assureTimeValid(Vote.class.getSimpleName(), "update");
        checkNotFoundWithId(voteService.save(vote, userId, restaurantId), id);
    }

    public Vote create(int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("create vote for user {} for restaurant {}", userId, restaurantId);
        Vote vote = new Vote();
        assureTimeValid(Vote.class.getSimpleName(), "create");
        return voteService.save(vote, userId, restaurantId);
    }
}
