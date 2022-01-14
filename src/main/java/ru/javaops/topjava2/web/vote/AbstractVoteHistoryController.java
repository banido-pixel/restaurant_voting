package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.SecurityUtil;

import java.util.List;

@Slf4j
public class AbstractVoteHistoryController {

    @Autowired
    private VoteService voteService;

    public List<Vote> getAllForUser() {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getAllForUser(userId);
    }
}
