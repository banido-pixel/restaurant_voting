package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT_ID = 1;
    public static final int NOT_FOUND = 120;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Арарат");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "Белый Аист");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID + 2, "Прага");

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedName");
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "new");
    }
}
