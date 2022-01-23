package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.service.VoteService;
import com.github.banido_pixel.restaurant_voting.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.Clock;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.*;

@Slf4j
public abstract class AbstractVoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private Clock clock;

    public List<Vote> getAll() {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getAll(userId);
    }

    public Vote get(int id) {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.get(id, userId).orElseThrow();
    }

    public void delete(int id) {
        int userId = SecurityUtil.authId();
        log.info("delete vote {} for user {}", id, userId);
        voteService.delete(id, userId);
    }

    public void update(Vote vote, int id, int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("update vote {} for user {} for restaurant {}", vote, userId, restaurantId);
        assureIdConsistent(vote, id);
        Assert.notNull(vote, "vote must not be null");
        assureTimeValid(Vote.class.getSimpleName(), "update", clock);
        checkNotFoundWithId(voteService.save(vote, userId, restaurantId), id);
    }

    public Vote create(int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("create vote for user {} for restaurant {}", userId, restaurantId);
        Vote vote = new Vote();
        assureTimeValid(Vote.class.getSimpleName(), "create", clock);
        return voteService.save(vote, userId, restaurantId);
    }
}
