package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import com.github.banido_pixel.restaurant_voting.util.JsonUtil;
import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import com.github.banido_pixel.restaurant_voting.web.FixedIllegalClockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.UPDATE_END_TIME;
import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.USER_MAIL;
import static com.github.banido_pixel.restaurant_voting.web.vote.VoteController.REST_URL;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FixedIllegalClockConfig.class)
class VoteControllerIllegalTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterAllowedTime() throws Exception {
        VoteTo updated = VoteTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + VoteTestData.VOTE_ID)
                .param("restaurantId", RESTAURANT_ID + 1 + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UPDATE_END_TIME.toString())));
    }
}