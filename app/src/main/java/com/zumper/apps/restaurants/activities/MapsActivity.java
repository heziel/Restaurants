package com.zumper.apps.restaurants.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.zumper.apps.restaurants.R;
import com.zumper.apps.restaurants.model.Restaurant;
import com.zumper.apps.restaurants.presenter.Contract;
import com.zumper.apps.restaurants.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Contract.View,
        GoogleMap.OnMarkerClickListener,
        LocationListener {

    private static final int INTERVAL = 1000;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private PresenterImpl presenter;
    private ArrayList<Restaurant> myRestaurantsList;

    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }

        // Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToListView();
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter = new PresenterImpl(this);
        presenter.fetchRestaurantsFromApi();
    }

    // switch to listView
    private void switchToListView() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Map Marker onClick
        mMap.setOnMarkerClickListener(this);

        mMap.setMinZoomPreference(12f);

        // Add a marker in Sydney and move the camera
        LatLng sanFrancisco = new LatLng(37.773972,-122.431297);

     //   mMap.addMarker(new MarkerOptions().position(sanFrancisco).title("San Francisco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sanFrancisco));

        buildGoogleApiClient();
    }

    // Check if Google Play Service is Available.
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    // Main entry point for Google Play services integration
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "lat=" + location.getLatitude() + " long" + location.getLongitude());
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    // Create locations on the map.
    @Override
    public void updateView(List<Restaurant> restaurantsList) {

        myRestaurantsList = new ArrayList<>(restaurantsList);

        for ( Restaurant restaurant: restaurantsList){
            Double lat = restaurant.getGeometry().getLocation().getLat();
            Double lng = restaurant.getGeometry().getLocation().getLng();
            String placeName = restaurant.getName();
            String vicinity = restaurant.getVicinity();
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(lat, lng);

            // Position of Marker on Map
            markerOptions.position(latLng);

            // Adding Title to the Marker
            markerOptions.title(placeName + " : " + vicinity);

            // Adding Marker to the Camera.
            Marker m = mMap.addMarker(markerOptions);

            // Adding colour to the marker
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            // move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }
    }

    // Set the onClick Listener on Marker when clicking on Item.
    @Override
    public boolean onMarkerClick(Marker marker) {
        String id = marker.getId();
        int id_number = Integer.valueOf(id.substring(1,id.length()));

        Restaurant restaurant = myRestaurantsList.get(id_number);

        // New Intent and  pass my object
        Intent intent = new Intent(this, DetailsActivity.class);
        Gson gson = new Gson();
        String myJson = gson.toJson(restaurant);
        intent.putExtra("myjson", myJson);
        startActivity(intent);

        return true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
