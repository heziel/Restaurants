package com.zumper.apps.restaurants.presenter;

import com.zumper.apps.restaurants.model.Restaurant;

import java.util.List;

/**
 * The Contract describes the interaction between the Views and the presenter.
 * MVP pattern
 */

public interface Contract {

    public interface View {

        void updateView(List<Restaurant> restaurantsList);

    }


    public interface Presenter<V> {

        void attachView(V v);

        void fetchRestaurantsFromApi();

    }
}
