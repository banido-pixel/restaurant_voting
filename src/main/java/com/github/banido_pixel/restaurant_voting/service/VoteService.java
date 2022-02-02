package com.github.banido_pixel.restaurant_voting.service;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.repository.RestaurantRepository;
import com.github.banido_pixel.restaurant_voting.repository.UserRepository;
import com.github.banido_pixel.restaurant_voting.repository.VoteRepository;
import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.VoteUtil.createTo;
import static com.github.banido_pixel.restaurant_voting.util.VoteUtil.getTos;
import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.assureTimeValid;
import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class VoteService {

    @Autowired
    private Clock clock;

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public List<VoteTo> getAll(int userId) {
        return getTos(voteRepository.getAll(userId)).stream().sorted(Comparator.comparing(VoteTo::getDate)).toList();
    }

    public VoteTo getTo(int id, int userId) {
        return createTo(voteRepository.getTo(id, userId).orElseThrow());
    }

    public Vote get(int id, int userId) {
        return voteRepository.get(id, userId).orElseThrow();
    }

    public VoteTo getByDate(LocalDate date, int userId) {
        return createTo(voteRepository.getByDate(date, userId).orElseThrow());
    }

    @Transactional
    public void update(VoteTo voteTo, int userId) {
        assureTimeValid(clock);
        Vote vote = get(voteTo.id(), userId);
        setRestaurantFromTo(vote, voteTo.getRestaurantId());
        checkNotFoundWithId(voteRepository.save(vote), vote.id());
    }

    @Transactional
    public VoteTo create(VoteTo voteTo, int userId) {
        Vote vote = new Vote();
        vote.setDate(LocalDate.now());
        vote.setUser(userRepository.getById(userId));
        setRestaurantFromTo(vote, voteTo.getRestaurantId());
        voteRepository.save(vote);
        return createTo(vote);
    }

    private void setRestaurantFromTo(Vote vote, Integer voteTo) {
        vote.setRestaurant(restaurantRepository.getById(voteTo));
    }
}
