package com.github.banido_pixel.restaurant_voting.web.vote;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.service.VoteService;
import com.github.banido_pixel.restaurant_voting.web.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping()
    @Operation(summary = "getAll")
    public List<Vote> getAll() {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.getAll(userId);
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public Vote get(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("get vote history for user {}", userId);
        return voteService.get(id, userId).orElseThrow();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete")
    public void delete(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("delete vote {} for user {}", id, userId);
        voteService.delete(id, userId);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id, @RequestParam int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("update vote {} for user {} for restaurant {}", vote, userId, restaurantId);
        assureIdConsistent(vote, id);
        Assert.notNull(vote, "vote must not be null");
        assureTimeValid(Vote.class.getSimpleName(), "update", clock);
        checkNotFoundWithId(voteService.save(vote, userId, restaurantId), id);
    }

    @PostMapping()
    @Operation(summary = "create")
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId) {
        int userId = SecurityUtil.authId();
        log.info("create vote for user {} for restaurant {}", userId, restaurantId);
        Vote vote = new Vote();
        assureTimeValid(Vote.class.getSimpleName(), "create", clock);
        Vote created = voteService.save(vote, userId, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
