package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.service.VoteService;
import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import com.github.banido_pixel.restaurant_voting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/profile/votes/";

    @Autowired
    private VoteService voteService;

    @GetMapping
    @Operation(summary = "getAll")
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote history for user {}", authUser.id());
        return voteService.getAll(authUser.id());
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote history for user {}", authUser.id());
        return voteService.getTo(id, authUser.id());
    }

    @GetMapping("by-date")
    @Operation(summary = "getByDate")
    public VoteTo getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote history for user {}", authUser.id());
        return voteService.getByDate(date, authUser.id());
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        int restaurantId = voteTo.getRestaurantId();
        log.info("update vote {} for user {} for restaurant {}", voteTo, authUser.id(), restaurantId);
        assureIdConsistent(voteTo, id);
        voteService.update(voteTo, authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create")
    public ResponseEntity<VoteTo> createWithLocation(@Valid @RequestBody VoteTo voteTo,
                                                     @AuthenticationPrincipal AuthUser authUser) {
        int restaurantId = voteTo.getRestaurantId();
        log.info("create vote for user {} for restaurant {}", authUser.id(), restaurantId);
        VoteTo created = voteService.create(voteTo, authUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
