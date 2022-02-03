package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menu-items")
public class MenuItemController extends AbstractMenuItemController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/menu-items/";

    @GetMapping("today")
    @Operation(summary = "getAllToday")
    @Cacheable
    public List<MenuItem> getAllToday(@PathVariable int restaurantId) {
        return super.getAllToday(restaurantId);
    }
}
