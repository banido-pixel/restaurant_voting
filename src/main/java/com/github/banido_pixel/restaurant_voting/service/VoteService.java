package com.github.banido_pixel.restaurant_voting.service;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.repository.RestaurantRepository;
import com.github.banido_pixel.restaurant_voting.repository.UserRepository;
import com.github.banido_pixel.restaurant_voting.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    public Optional<Vote> get(int id, int userId) {
        return voteRepository.get(id, userId);
    }

    public Optional<Vote> getByDate(LocalDate date, int userId) {
        return voteRepository.getByDate(date, userId);
    }

    public void delete(int id, int userId) {
        voteRepository.deleteExisted(id, userId);
    }

    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.id(), userId).isEmpty()) {
            return null;
        }
        if (vote.isNew()) {
            vote.setDate(LocalDate.now());
            vote.setUser(userRepository.findById(userId).orElseThrow());
        }
        vote.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());
        return voteRepository.save(vote);
    }
}
