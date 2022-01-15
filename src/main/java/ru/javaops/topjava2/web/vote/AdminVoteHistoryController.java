package ru.javaops.topjava2.web.vote;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Vote;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteHistoryController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteHistoryController extends AbstractVoteHistoryController{

    static final String REST_URL = "/api/admin/votes/";

    @GetMapping()
    public List<Vote> getAllForUser() {
        return super.getAllForUser();
    }
}
