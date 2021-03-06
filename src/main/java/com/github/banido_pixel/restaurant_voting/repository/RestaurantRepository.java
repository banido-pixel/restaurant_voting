package com.github.banido_pixel.restaurant_voting.repository;

import com.github.banido_pixel.restaurant_voting.model.Restaurant;
import com.github.banido_pixel.restaurant_voting.to.RestaurantTo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT new com.github.banido_pixel.restaurant_voting.to.RestaurantTo(r.id, r.name, (" +
            "SELECT COUNT(v.date) FROM Vote v WHERE v.date=:date AND v.restaurant.id=r.id)) FROM Restaurant r")
    List<RestaurantTo> getAllWithVotesWithDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menuItems mi WHERE mi.menuDate=current_date")
    List<Restaurant> getAllWithMenuItems();
}
