package ru.javaops.topjava2.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Dish;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishController extends AbstractDishController{

    static final String REST_URL = "/api/restaurants/{restaurantId}/dishes/";

    @GetMapping
    @Operation(summary = "getAll")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @GetMapping("previous")
    @Operation(summary = "getAllWithDate")
    public List<Dish> getAllWithDate(@PathVariable int restaurantId,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllWithDate(restaurantId, date);
    }
}
