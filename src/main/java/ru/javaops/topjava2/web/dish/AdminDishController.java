package ru.javaops.topjava2.web.dish;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractDishController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @GetMapping("/previous")
    public List<Dish> getAllWithDate(@PathVariable int restaurantId,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllWithDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        super.update(dish, id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable int restaurantId) {
        Dish created = super.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
