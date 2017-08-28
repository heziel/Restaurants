package com.zumper.apps.restaurants.model;

import java.util.List;

/**
 * Restaurants interface.
 */

public interface Restaurants {

    void loadItems(List<Restaurant> restaurantList);

    List<Restaurant> getItems();

}
