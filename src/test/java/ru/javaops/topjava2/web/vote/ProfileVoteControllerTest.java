package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.vote.ProfileVoteController.REST_URL;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {

    static final String TEST_URL = REST_URL.replace("{restaurantId}", RESTAURANT_ID + "");

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(TEST_URL + VOTE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.findById(VOTE_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception{
        voteRepository.deleteExisted(VOTE_ID);
        Vote newVote = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());
        Vote created = VOTE_MATCHER.readFromJson(actions);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }
}