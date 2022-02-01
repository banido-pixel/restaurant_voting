package com.github.banido_pixel.restaurant_voting.web.restaurant;

import com.github.banido_pixel.restaurant_voting.model.NamedEntity;
import com.github.banido_pixel.restaurant_voting.model.Restaurant;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_ID = 1;
    public static final int NOT_FOUND = 120;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Арарат");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "Белый Аист");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID + 2, "Прага");

    public static final List<Restaurant> restaurants = Stream.of(restaurant1,restaurant2,restaurant3)
            .sorted(Comparator.comparing(NamedEntity::getName)).toList();

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedName");
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "new");
    }
}
