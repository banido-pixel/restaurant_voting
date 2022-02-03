package com.github.banido_pixel.restaurant_voting.web.restaurant;

import com.github.banido_pixel.restaurant_voting.model.Restaurant;
import com.github.banido_pixel.restaurant_voting.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants/";

    @GetMapping
    @Operation(summary = "getAll")
    @Cacheable(key = "'getAll'")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("with-menu")
    @Operation(summary = "getAll with menu")
    @Cacheable(key = "'getAllWithMenu'")
    public List<Restaurant> getAllWithMenuItems() {
        return super.getAllWithMenuItems();
    }

    @GetMapping("with-rating-today")
    @Operation(summary = "getAllWithVotes")
    public List<RestaurantTo> getAllWithVotes() {
        return super.getAllWithVotes();
    }

    @GetMapping("with-rating-by-date")
    @Operation(summary = "getAllWithVotesWithDate")
    public List<RestaurantTo> getAllWithDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllWithVotesWithDate(date);
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }
}
