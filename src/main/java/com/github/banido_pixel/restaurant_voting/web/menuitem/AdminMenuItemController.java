package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menu-items")
public class AdminMenuItemController extends AbstractMenuItemController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu-items/";

    @GetMapping("today")
    @Operation(summary = "getAllToday")
    @Cacheable
    public List<MenuItem> getAllToday(@PathVariable int restaurantId) {
        return super.getAllToday(restaurantId);
    }

    @GetMapping("by-date")
    @Operation(summary = "getAllByDate")
    public List<MenuItem> getAllWithDate(@PathVariable int restaurantId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllWithDate(restaurantId, date);
    }

    @GetMapping
    @Operation(summary = "getAll")
    public List<MenuItem> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public MenuItem get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Caching(evict = {
            @CacheEvict(value = "menu-items", allEntries = true),
            @CacheEvict(value = "restaurants", key = "'getAllWithMenu'")
    })
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    @Caching(evict = {
            @CacheEvict(value = "menu-items", allEntries = true),
            @CacheEvict(value = "restaurants", key = "'getAllWithMenu'")
    })
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id, @PathVariable int restaurantId) {
        super.update(menuItem, id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create")
    @Caching(evict = {
            @CacheEvict(value = "menu-items", allEntries = true),
            @CacheEvict(value = "restaurants", key = "'getAllWithMenu'")
    })
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        MenuItem created = super.create(menuItem, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
