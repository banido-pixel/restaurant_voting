package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.util.JsonUtil;
import com.github.banido_pixel.restaurant_voting.web.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.repository.VoteRepository;
import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import com.github.banido_pixel.restaurant_voting.web.FixedLegalClockConfig;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.USER_MAIL;
import static com.github.banido_pixel.restaurant_voting.web.vote.ProfileVoteController.REST_URL;

@SpringBootTest(classes = FixedLegalClockConfig.class)
class ProfileVoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.userVote1, VoteTestData.userVote2, VoteTestData.userVote3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VoteTestData.VOTE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.findById(VoteTestData.VOTE_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForeign() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VoteTestData.ADMIN_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + VoteTestData.VOTE_ID)
                .param("restaurantId", RESTAURANT_ID + 1 + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.VOTE_MATCHER.assertMatch(voteRepository.getById(VoteTestData.VOTE_ID), VoteTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        voteRepository.deleteExisted(VoteTestData.VOTE_ID);
        Vote newVote = VoteTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());
        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(actions);
        int newId = created.id();
        newVote.setId(newId);
        VoteTestData.VOTE_MATCHER.assertMatch(created, newVote);
        VoteTestData.VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createSecondPerDay() throws Exception {
        Vote newVote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_SECOND_VOTE_PER_DATE)));
    }
}