package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController{

    static final String REST_URL = "api/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }
}
