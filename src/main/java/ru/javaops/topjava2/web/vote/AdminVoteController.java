package ru.javaops.topjava2.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/votes/";

    @GetMapping()
    @Operation(summary = "getAll")
    public List<Vote> getAll() {
        return super.getAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "get")
    public Vote get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update")
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id, @RequestParam int restaurantId) {
        super.update(vote, id, restaurantId);
    }

    @PostMapping()
    @Operation(summary = "create")
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId) {
        Vote created = super.create(restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
