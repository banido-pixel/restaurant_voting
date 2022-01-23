package com.github.banido_pixel.restaurant_voting.repository;

import com.github.banido_pixel.restaurant_voting.model.Dish;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    default void deleteExisted(int id, int restaurantId) {
        ValidationUtil.checkModification(delete(id, restaurantId), id);
    }

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.menuDate=current_date ")
    Optional<List<Dish>> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.menuDate=:date")
    Optional<List<Dish>> getAllWithDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Optional<Dish> get(@Param("id") int id, @Param("restaurantId") int restaurantId);

}
