package com.github.banido_pixel.restaurant_voting.repository;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem i WHERE i.id=:id AND i.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    default void deleteExisted(int id, int restaurantId) {
        ValidationUtil.checkModification(delete(id, restaurantId), id);
    }

    @Query("SELECT i FROM MenuItem i WHERE i.restaurant.id=:restaurantId AND i.menuDate=:date")
    List<MenuItem> getAllWithDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT i FROM MenuItem i WHERE i.restaurant.id=:restaurantId")
    List<MenuItem> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT i FROM MenuItem i WHERE i.id=:id AND i.restaurant.id=:restaurantId")
    Optional<MenuItem> get(@Param("id") int id, @Param("restaurantId") int restaurantId);

}
