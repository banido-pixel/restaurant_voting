package com.github.banido_pixel.restaurant_voting.web.dish;

import com.github.banido_pixel.restaurant_voting.model.Dish;
import com.github.banido_pixel.restaurant_voting.repository.DishRepository;
import com.github.banido_pixel.restaurant_voting.util.JsonUtil;
import com.github.banido_pixel.restaurant_voting.web.AbstractControllerTest;
import com.github.banido_pixel.restaurant_voting.web.FixedLegalClockConfig;
import com.github.banido_pixel.restaurant_voting.web.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.banido_pixel.restaurant_voting.web.dish.AdminDishController.REST_URL;
import static com.github.banido_pixel.restaurant_voting.web.dish.DishTestData.*;
import static com.github.banido_pixel.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static com.github.banido_pixel.restaurant_voting.web.user.UserTestData.ADMIN_MAIL;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FixedLegalClockConfig.class)
class AdminDishControllerTest extends AbstractControllerTest {

    static final String TEST_URL = REST_URL.replace("{restaurantId}", RESTAURANT_ID + "");

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL + DISH_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(TEST_URL + DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DISH_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(TEST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.getById(DISH_ID), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(actions);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicateName() throws Exception {
        Dish newDish = dishRepository.getById(DISH_ID);
        newDish.setId(null);
        perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_DISH_NAME)));
    }
}