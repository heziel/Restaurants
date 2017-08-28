package com.zumper.apps.restaurants.model;

import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantsList keeps a list of all the Restaurants.
 */

public class RestaurantsList implements Restaurants {

    private List<Restaurant> restaurantsList;

    public RestaurantsList() {
        restaurantsList = new ArrayList<>();
    }

    @Override
    public void loadItems(List<Restaurant> restaurantsList) {
        setRestaurants(restaurantsList);
    }

    @Override
    public List<Restaurant> getItems() {
        return restaurantsList;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantsList;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurantsList = restaurants;
    }
}
