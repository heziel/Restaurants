package com.zumper.apps.restaurants.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zumper.apps.restaurants.R;
import com.zumper.apps.restaurants.model.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.rating)
    RatingBar ratingBar;


    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        // get the restaurant information
        Intent intent = getIntent();
        Gson gson = new Gson();
        Restaurant restaurant = gson.fromJson(getIntent().getStringExtra("myjson"), Restaurant.class);

        // fill the picture
        Glide.with(this)
                .load(restaurant.getPhotos().get(0).getPhotoReference())
                .placeholder(R.drawable.restaurant)
                .into(image);

        double rating = restaurant.getRating();
        ratingBar.setRating((float) rating);

        name.setText(restaurant.getName());

        address.setText(restaurant.getVicinity());

    }
}
