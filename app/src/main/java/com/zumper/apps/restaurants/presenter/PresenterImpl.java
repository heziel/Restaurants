package com.zumper.apps.restaurants.presenter;

import android.util.Log;

import com.zumper.apps.restaurants.model.ApiObject;
import com.zumper.apps.restaurants.model.Restaurants;
import com.zumper.apps.restaurants.model.RestaurantsList;
import com.zumper.apps.restaurants.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This Class Represent the implementation of the Presenter
 * In MVP pattern
 */

public class PresenterImpl implements Contract.Presenter<Contract.View> {

    private static final String API_URL = "https://maps.googleapis.com/maps/";
    private static final String API_KEY = "AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk";

    private int PROXIMITY_RADIUS = 1000;

    private Restaurants restaurantsList;
    private Contract.View restaurantsView;


    private String latitude = "37.773972";
    private String longitude = "-122.431297";

    public PresenterImpl(Contract.View restaurantsView) {
        attachView(restaurantsView);
        restaurantsList = new RestaurantsList();
    }

    @Override
    public void attachView(Contract.View view) {
        this.restaurantsView = view;
    }

    @Override
    public void fetchRestaurantsFromApi() {

        // Create Retrofit Client
        JSONPlaceholderService retrofitClient =
                RetrofitClient.getClient(API_URL).create(JSONPlaceholderService.class);

        Call<ApiObject> call = retrofitClient.getNearbyPlaces("restaurant", latitude + "," + longitude, PROXIMITY_RADIUS);

        call.enqueue(new Callback<ApiObject>() {
            @Override
            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                if (response.isSuccessful()) {

                    // Load the item into the photos List.
                    restaurantsList.loadItems(response.body().getResults());

                    // Send the List to the view for display.
                    restaurantsView.updateView(restaurantsList.getItems());


                    Log.d("MainActivity", "Restaurants loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("MainActivity", "Api load failed statusCode:" + statusCode);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("MainActivity", "error loading from API " + t.getMessage());
                //restaurantsView.errorDialogBox();
            }
        });
    }

    public String buildApiUrl() {
        String url = API_URL + " " + API_KEY;

        return url;
    }

}
