package com.github.banido_pixel.restaurant_voting.web.dish;

import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.banido_pixel.restaurant_voting.web.dish.DishTestData.*;
import static com.github.banido_pixel.restaurant_voting.web.dish.ProfileDishController.REST_URL;
import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileDishControllerTest extends AbstractControllerTest {

    static final String TEST_URL = REST_URL.replace("{restaurantId}", RESTAURANT_ID + "");

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2, dish3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllWithDate() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL + "/previous")
                .param("date", MENU_DATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish4, dish5, dish6));
    }
}