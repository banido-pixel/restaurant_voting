package com.github.banido_pixel.restaurant_voting.web.dish;

import com.github.banido_pixel.restaurant_voting.model.Dish;
import com.github.banido_pixel.restaurant_voting.repository.DishRepository;
import com.github.banido_pixel.restaurant_voting.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import com.github.banido_pixel.restaurant_voting.web.FixedIllegalClockConfig;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.banido_pixel.restaurant_voting.web.dish.AdminDishController.REST_URL;
import static com.github.banido_pixel.restaurant_voting.web.dish.DishTestData.*;
import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.ADMIN_MAIL;

@SpringBootTest(classes = FixedIllegalClockConfig.class)
class AdminDishControllerIllegalTest extends AbstractControllerTest {

    static final String TEST_URL = REST_URL.replace("{restaurantId}", RESTAURANT_ID + "");

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createAfterAllowedTime() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("11:00")));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateAfterAllowedTime() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(TEST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("11:00")));
    }
}