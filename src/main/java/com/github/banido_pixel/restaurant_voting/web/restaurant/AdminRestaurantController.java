package com.github.banido_pixel.restaurant_voting.web.restaurant;

import com.github.banido_pixel.restaurant_voting.model.Restaurant;
import com.github.banido_pixel.restaurant_voting.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants/";

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

    @GetMapping("{id}")
    @Operation(summary = "get")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete")
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create")
    @CacheEvict(key = "'getAll'")
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
