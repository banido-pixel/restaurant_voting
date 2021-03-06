package com.github.banido_pixel.restaurant_voting.service;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.repository.MenuItemRepository;
import com.github.banido_pixel.restaurant_voting.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public Optional<MenuItem> get(int id, int restaurantId) {
        return menuItemRepository.get(id, restaurantId);
    }

    public List<MenuItem> getAllToday(int restaurantId) {
        return menuItemRepository.getAllWithDate(restaurantId, LocalDate.now());
    }

    public List<MenuItem> getAllWithDate(int restaurantId, LocalDate date) {
        return menuItemRepository.getAllWithDate(restaurantId, date);
    }

    public List<MenuItem> getAll(int restaurantId) {
        return menuItemRepository.getAll(restaurantId);
    }

    public void delete(int id, int restaurantId) {
        menuItemRepository.deleteExisted(id, restaurantId);
    }

    @Transactional
    public MenuItem save(MenuItem menuItem, int restaurantId) {
        if (!menuItem.isNew() && get(menuItem.id(), restaurantId).isEmpty()) {
            return null;
        }
        menuItem.setRestaurant(restaurantRepository.getById(restaurantId));
        return menuItemRepository.save(menuItem);
    }
}
