package com.zumper.apps.restaurants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.zumper.apps.restaurants.R;
import com.zumper.apps.restaurants.adapter.RestaurantAdapter;
import com.zumper.apps.restaurants.model.Restaurant;
import com.zumper.apps.restaurants.presenter.Contract;
import com.zumper.apps.restaurants.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements Contract.View{
    @BindView(R.id.rvItems) RecyclerView recyclerView;

    private ArrayList<Restaurant> myRestaurantsList;

    private RestaurantAdapter restaurantAdapter;
    private PresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        // new Presenter
        presenter = new PresenterImpl(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        // Floating Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToMapView();
            }
        });

        recyclerViewPreparation();

    }

    private void switchToMapView() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateView(List<Restaurant> restaurantsList) {
        restaurantAdapter.addAll(restaurantsList);
    }


    // Prepare recyclerView adapter.
    private void recyclerViewPreparation() {
        // Fetch all photos
        presenter.fetchRestaurantsFromApi();

        // Attach the adapter to the recyclerView to populate items
        restaurantAdapter = new RestaurantAdapter(this, new ArrayList<Restaurant>());
        recyclerView.setAdapter(restaurantAdapter);

        //  Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }


}
