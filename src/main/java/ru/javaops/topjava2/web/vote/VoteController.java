package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/";

    @Autowired
    private VoteService voteService;

    @GetMapping("profile/votes")
    public List<Vote> getAllForUser() {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getAllForUser(userId);
    }

    @GetMapping("/restaurants/{restaurantId}/votes/")
    public Integer getAmountForRestaurantWithDate(@PathVariable int restaurantId,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all votes for restaurant {} with date {}", restaurantId, date);
        return voteService.getAmountForRestaurantWithDate(restaurantId, date);
    }

    @DeleteMapping("/restaurants/{restaurantId}/votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("delete vote {} for user {} for restaurant with id = {}", id,userId, restaurantId);
        voteService.delete(id, userId);
    }

    @PutMapping(value = "restaurants/{restaurantId}/votes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id, @PathVariable int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("update vote {} for user {} for restaurant {}", vote, userId, restaurantId);
        assureIdConsistent(vote, id);
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteService.save(vote, userId, restaurantId), id);
    }

    @PostMapping(value = "/restaurants/{restaurantId}/")
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("create vote for user {} for restaurant {}", userId, restaurantId);
        Vote vote = new Vote();
        Vote created = voteService.save(vote, userId, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/{restaurantId}/votes/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
