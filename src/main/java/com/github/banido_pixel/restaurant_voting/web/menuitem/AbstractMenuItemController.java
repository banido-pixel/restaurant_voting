package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.service.MenuItemService;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
public abstract class AbstractMenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private Clock clock;

    public List<MenuItem> getAllToday(int restaurantId) {
        log.info("getAll dishes for restaurant with id = {}", restaurantId);
        return menuItemService.getAllToday(restaurantId).orElseThrow();
    }

    public List<MenuItem> getAllWithDate(int restaurantId, LocalDate date) {
        log.info("getAll dishes with date {} for restaurant with id = {}", date, restaurantId);
        return menuItemService.getAllWithDate(restaurantId, date).orElseThrow();
    }

    public MenuItem get(int id, int restaurantId) {
        log.info("get dish {} for restaurant with id = {}", id, restaurantId);
        return menuItemService.get(id, restaurantId).orElseThrow();
    }

    public void delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant with id = {}", id, restaurantId);
        menuItemService.delete(id, restaurantId);
    }

    public void update(MenuItem menuItem, int id, int restaurantId) {
        log.info("update dish {} for restaurant with id = {}", menuItem, restaurantId);
        ValidationUtil.assureIdConsistent(menuItem, id);
        Assert.notNull(menuItem, "dish must not be null");
        ValidationUtil.assureTimeValid(MenuItem.class.getSimpleName(), "update", clock);
        checkNotFoundWithId(menuItemService.save(menuItem, restaurantId), id);
    }

    public MenuItem create(MenuItem menuItem, int restaurantId) {
        log.info("create dish {} for restaurant with id = {}", menuItem, restaurantId);
        ValidationUtil.checkNew(menuItem);
        Assert.notNull(menuItem, "dish must not be null");
        ValidationUtil.assureTimeValid(MenuItem.class.getSimpleName(), "create", clock);
        return menuItemService.save(menuItem, restaurantId);
    }
}
