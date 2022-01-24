package com.github.banido_pixel.restaurant_voting.service;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.repository.MenuItemRepository;
import com.github.banido_pixel.restaurant_voting.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<MenuItem> get(int id, int restaurantId) {
        return menuItemRepository.get(id, restaurantId);
    }

    public Optional<List<MenuItem>> getAll(int restaurantId) {
        return menuItemRepository.getAll(restaurantId);
    }

    public Optional<List<MenuItem>> getAllWithDate(int restaurantId, LocalDate date) {
        return menuItemRepository.getAllWithDate(restaurantId, date);
    }

    public void delete(int id, int restaurantId) {
        menuItemRepository.deleteExisted(id, restaurantId);
    }

    @Transactional
    public MenuItem save(MenuItem menuItem, int restaurantId) {
        if (!menuItem.isNew() && get(menuItem.id(), restaurantId).isEmpty()) {
            return null;
        }
        menuItem.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());
        return menuItemRepository.save(menuItem);
    }
}
