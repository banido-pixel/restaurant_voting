package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.service.VoteService;
import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import com.github.banido_pixel.restaurant_voting.web.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.*;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/profile/votes/";

    @Autowired
    private VoteService voteService;

    @Autowired
    private Clock clock;

    @GetMapping
    @Operation(summary = "getAll")
    public List<VoteTo> getAll() {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getAll(userId);
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public VoteTo get(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getTo(id, userId);
    }

    @GetMapping("by-date")
    @Operation(summary = "getByDate")
    public VoteTo   getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getByDate(date, userId);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable int id) {
        int userId = SecurityUtil.authId();
        int restaurantId = voteTo.getRestaurantId();
        log.info("update vote {} for user {} for restaurant {}", voteTo, userId, restaurantId);
        assureIdConsistent(voteTo, id);
        Assert.notNull(voteTo, "vote must not be null");
        assureTimeValid(clock);
        voteService.update(voteTo, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create")
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody VoteTo voteTo) {
        int userId = SecurityUtil.authId();
        int restaurantId = voteTo.getRestaurantId();
        log.info("create vote for user {} for restaurant {}", userId, restaurantId);
        Vote created = voteService.create(voteTo, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
