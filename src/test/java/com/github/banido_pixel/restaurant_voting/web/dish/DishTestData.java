package com.github.banido_pixel.restaurant_voting.web.dish;

import com.github.banido_pixel.restaurant_voting.model.Dish;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int DISH_ID = 1;
    public static final String MENU_DATE = LocalDate.of(2022,1,11).toString();

    public static final Dish dish1 = new Dish(DISH_ID, "Уха",250);
    public static final Dish dish2 = new Dish(DISH_ID+1, "Весенний салат",100);
    public static final Dish dish3 = new Dish(DISH_ID+2, "Плов",200);
    public static final Dish dish4 = new Dish(DISH_ID+3, "Борщ",250);
    public static final Dish dish5 = new Dish(DISH_ID+4, "Цезарь",100);
    public static final Dish dish6 = new Dish(DISH_ID+5, "Куриное рагу",150);

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "UpdatedName", 133);
    }

    public static Dish getNew() {
        return new Dish(null, "new", 122);
    }
}
