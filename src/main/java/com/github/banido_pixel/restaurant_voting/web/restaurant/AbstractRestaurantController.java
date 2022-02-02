package com.github.banido_pixel.restaurant_voting.web.restaurant;

import com.github.banido_pixel.restaurant_voting.model.Restaurant;
import com.github.banido_pixel.restaurant_voting.repository.RestaurantRepository;
import com.github.banido_pixel.restaurant_voting.to.RestaurantTo;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
@CacheConfig(cacheNames = "restaurants")
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<RestaurantTo> getAllWithVotes() {
        log.info("getAll restaurants");
        return restaurantRepository.getAllWithVotesWithDate(LocalDate.now())
                .stream().sorted(Comparator.comparing(RestaurantTo::getVotesAmount).reversed()).toList();
    }

    public List<RestaurantTo> getAllWithVotesWithDate(LocalDate date) {
        log.info("getAll restaurants with votes with date {}", date);
        return restaurantRepository.getAllWithVotesWithDate(date)
                .stream().sorted(Comparator.comparing(RestaurantTo::getVotesAmount).reversed()).toList();
    }

    @Cacheable
    public ResponseEntity<Restaurant> get(int id) {
        log.info("get restaurant by id = {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        log.info("delete restaurant with id = {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Restaurant restaurant, int id) {
        log.info("update restaurant {}", restaurant);
        ValidationUtil.assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @CacheEvict(allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }
}
