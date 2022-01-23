package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.repository.VoteRepository;
import com.github.banido_pixel.restaurant_voting.util.JsonUtil;
import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import com.github.banido_pixel.restaurant_voting.web.FixedIllegalClockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.USER_MAIL;
import static com.github.banido_pixel.restaurant_voting.web.vote.ProfileVoteController.REST_URL;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FixedIllegalClockConfig.class)
class ProfileVoteControllerIllegalTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createAfterAllowedTime() throws Exception {
        voteRepository.deleteExisted(VoteTestData.VOTE_ID);
        Vote newVote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("11:00")));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterAllowedTime() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + VoteTestData.VOTE_ID)
                .param("restaurantId", RESTAURANT_ID + 1 + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("11:00")));
    }
}