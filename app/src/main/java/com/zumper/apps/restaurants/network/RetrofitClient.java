package com.zumper.apps.restaurants.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
*   To Issue network requests to a REST API with Retrofit,
*   Create Retrofit Instance.
*/

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
