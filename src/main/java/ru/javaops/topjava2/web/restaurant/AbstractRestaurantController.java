package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.getAll().orElseThrow();
    }

    public List<RestaurantTo> getAllWithDate(LocalDate date) {
        log.info("getAll restaurants with date {}", date);
        return restaurantRepository.getAllWithDate(date).orElseThrow();
    }

    public ResponseEntity<Restaurant> get(int id){
        log.info("get restaurant by id = {}", id);
        return ResponseEntity.of(Optional.of(restaurantRepository.findById(id).orElseThrow()));
    }

    public void delete(int id) {
        log.info("delete restaurant with id = {}", id);
        restaurantRepository.deleteExisted(id);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update restaurant {}", restaurant);
        assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    public Restaurant create (Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }
}
