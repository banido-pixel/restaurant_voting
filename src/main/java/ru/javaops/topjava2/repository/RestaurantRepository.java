package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT new ru.javaops.topjava2.to.RestaurantTo(r.id, r.name, (" +
            "SELECT COUNT(v.date) FROM Vote v WHERE v.date=current_date AND v.restaurant.id=r.id)) FROM Restaurant r")
    Optional<List<RestaurantTo>> getAllWithVotes();

    @Query("SELECT new ru.javaops.topjava2.to.RestaurantTo(r.id, r.name, (" +
            "SELECT COUNT(v.date) FROM Vote v WHERE v.date=:date AND v.restaurant.id=r.id)) FROM Restaurant r")
    Optional<List<RestaurantTo>> getAllWithDate(@Param("date") LocalDate date);
}
