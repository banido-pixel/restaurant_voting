package com.github.banido_pixel.restaurant_voting.web.menuitem;

import com.github.banido_pixel.restaurant_voting.model.MenuItem;
import com.github.banido_pixel.restaurant_voting.model.NamedEntity;
import com.github.banido_pixel.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final int MENU_ITEM_ID = 1;
    public static final String MENU_DATE = LocalDate.of(2022, 1, 11).toString();

    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM_ID, "Уха", 250, LocalDate.now());
    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM_ID + 1, "Весенний салат", 100, LocalDate.now());
    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM_ID + 2, "Плов", 200, LocalDate.now());
    public static final MenuItem menuItem4 = new MenuItem(MENU_ITEM_ID + 3, "Борщ", 250, LocalDate.parse(MENU_DATE));
    public static final MenuItem menuItem5 = new MenuItem(MENU_ITEM_ID + 4, "Цезарь", 100, LocalDate.parse(MENU_DATE));
    public static final MenuItem menuItem6 = new MenuItem(MENU_ITEM_ID + 5, "Куриное рагу", 150, LocalDate.parse(MENU_DATE));

    public static final List<MenuItem> menuItemsToday = Stream.of(menuItem1, menuItem2, menuItem3)
            .sorted(Comparator.comparing(NamedEntity::getName)).toList();

    public static final List<MenuItem> menuItemsByDate = Stream.of(menuItem4, menuItem5, menuItem6)
            .sorted(Comparator.comparing(NamedEntity::getName)).toList();

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM_ID, "UpdatedName", 133, LocalDate.now());
    }

    public static MenuItem getNew() {
        return new MenuItem(null, "new", 122, LocalDate.now());
    }
}
