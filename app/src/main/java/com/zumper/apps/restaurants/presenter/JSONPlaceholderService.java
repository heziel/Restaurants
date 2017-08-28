package com.zumper.apps.restaurants.presenter;

import com.zumper.apps.restaurants.model.ApiObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hezie on 8/27/2017.
 */

public interface JSONPlaceholderService {
    // Retrofit get annotation with our URL
    // And our method that will return us details of Restaurants.

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk")
    Call<ApiObject> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}
