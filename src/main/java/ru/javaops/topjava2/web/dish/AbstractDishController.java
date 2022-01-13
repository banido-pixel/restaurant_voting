package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.service.DishService;

import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@Slf4j
public class AbstractDishController {

    @Autowired
    private DishService dishService;

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll dishes for restaurant with id = {}", restaurantId);
        return dishService.getAll(restaurantId).orElseThrow();
    }

    public Dish get(int id, int restaurantId) {
        log.info("get dish {} for restaurant with id = {}", id, restaurantId);
        return dishService.get(id, restaurantId).orElseThrow();
    }

    public void delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant with id = {}", id, restaurantId);
        dishService.delete(id, restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        log.info("update dish {} for restaurant with id = {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishService.save(dish, restaurantId), id);
    }

    public Dish create(Dish dish, int restaurantId){
        log.info("create dish {} for restaurant with id = {}", dish, restaurantId);
        checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        return dishService.save(dish, restaurantId);
    }
}
