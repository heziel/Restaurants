package com.zumper.apps.restaurants.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zumper.apps.restaurants.R;
import com.zumper.apps.restaurants.activities.DetailsActivity;
import com.zumper.apps.restaurants.model.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RestaurantAdapter.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> restaurantList;
    private Context context;

    // describes an item view and its place within the RecyclerView.
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rlItem)
        RelativeLayout rlItem;
        @BindView(R.id.ivItem)
        ImageView ivPhoto;
        @BindView(R.id.tvItemRating)
        RatingBar tvItemRating;
        @BindView(R.id.tvItemName)
        TextView tvPhoto;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public RelativeLayout getMainLayout() {
            return rlItem;
        }
    }

    // RestaurantAdapter Constructor
    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);

       Glide.with(context)
                .load(restaurant.getPhotos().get(0).getPhotoReference())
                .placeholder(R.drawable.restaurant)
                .into(holder.ivPhoto);

        double rating = restaurant.getRating();
        holder.tvItemRating.setRating((float) rating);

        holder.tvPhoto.setText(restaurant.getName());

        // Set the onClick Listener on ViewHolder when clicking on Item.
        holder.getMainLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(restaurant);
                intent.putExtra("myjson", myJson);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // Add a list of items
    public void addAll(List<Restaurant> restaurantList) {
        this.restaurantList.addAll(restaurantList);
        notifyDataSetChanged();
    }
}