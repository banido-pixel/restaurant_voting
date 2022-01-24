package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileMenuItemController extends AbstractMenuItemController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/menu-items/";

    @GetMapping
    @Operation(summary = "getAll")
    public List<MenuItem> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @GetMapping("previous")
    @Operation(summary = "getAllWithDate")
    public List<MenuItem> getAllWithDate(@PathVariable int restaurantId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllWithDate(restaurantId, date);
    }
}
