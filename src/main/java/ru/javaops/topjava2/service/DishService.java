package ru.javaops.topjava2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Dish> get(int id, int restaurantId) {
        return dishRepository.get(id, restaurantId);
    }

    public Optional<List<Dish>> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public Optional<List<Dish>> getAllWithDate(int restaurantId, LocalDate date) {
        return dishRepository.getAllWithDate(restaurantId, date);
    }

    public void delete(int id, int restaurantId){
        dishRepository.deleteExisted(id, restaurantId);
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.id(), restaurantId).isEmpty()) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());
        return dishRepository.save(dish);
    }
}
