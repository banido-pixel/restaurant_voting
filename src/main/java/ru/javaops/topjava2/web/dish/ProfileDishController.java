package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Dish;

import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishController extends AbstractDishController{

    static final String REST_URL = "/api/restaurants/{restaurantId}/dishes";

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }
}
