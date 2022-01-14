package ru.javaops.topjava2.web.vote;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/votes";

    @GetMapping()
    public Integer getAmountForRestaurantWithDate(@PathVariable int restaurantId,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAmountForRestaurantWithDate(restaurantId, date);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id, @PathVariable int restaurantId) {
        super.update(vote, id, restaurantId);
    }

    @PostMapping()
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restaurantId) {
        Vote created = super.create(restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/{restaurantId}/votes/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
