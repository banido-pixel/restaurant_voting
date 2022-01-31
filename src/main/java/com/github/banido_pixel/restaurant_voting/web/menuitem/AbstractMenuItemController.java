package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.service.MenuItemService;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
public abstract class AbstractMenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    public List<MenuItem> getAllToday(int restaurantId) {
        log.info("getAll today menu items for restaurant with id = {}", restaurantId);
        return menuItemService.getAllToday(restaurantId).orElseThrow();
    }

    public List<MenuItem> getAllWithDate(int restaurantId, LocalDate date) {
        log.info("getAll menu items with date {} for restaurant with id = {}", date, restaurantId);
        return menuItemService.getAllWithDate(restaurantId, date).orElseThrow();
    }

    public List<MenuItem> getAll(int restaurantId) {
        log.info("getAll menu items for restaurant with id = {}", restaurantId);
        return menuItemService.getAll(restaurantId).orElseThrow();
    }

    public MenuItem get(int id, int restaurantId) {
        log.info("get menu item {} for restaurant with id = {}", id, restaurantId);
        return menuItemService.get(id, restaurantId).orElseThrow();
    }

    public void delete(int id, int restaurantId) {
        log.info("delete menu item {} for restaurant with id = {}", id, restaurantId);
        menuItemService.delete(id, restaurantId);
    }

    public void update(MenuItem menuItem, int id, int restaurantId) {
        log.info("update menu item {} for restaurant with id = {}", menuItem, restaurantId);
        ValidationUtil.assureIdConsistent(menuItem, id);
        Assert.notNull(menuItem, "menu item must not be null");
        checkNotFoundWithId(menuItemService.save(menuItem, restaurantId), id);
    }

    public MenuItem create(MenuItem menuItem, int restaurantId) {
        log.info("create menu item {} for restaurant with id = {}", menuItem, restaurantId);
        ValidationUtil.checkNew(menuItem);
        Assert.notNull(menuItem, "menu item must not be null");
        return menuItemService.save(menuItem, restaurantId);
    }
}
