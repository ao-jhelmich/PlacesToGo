package com.example.placestogo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placestogo.domain.location.GPS;
import com.example.placestogo.domain.location.GpsEnabled;
import com.example.placestogo.domain.places.Place;
import com.example.placestogo.domain.places.PlaceRepository;
import com.example.placestogo.domain.places.PlacesAdapter;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements GpsEnabled {
    protected PlaceRepository repository;
    protected PlacesAdapter adapter;
    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.repository = new PlaceRepository(this);
        this.adapter = new PlacesAdapter(this);
        this.gps = new GPS(this);

        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(this.adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        Place place = this.repository.getByPos(pos);
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }

    public PlaceRepository getRepository() {
        return this.repository;
    }

    public PlacesAdapter getAdapter() {
        return this.adapter;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (gps != null && gps.getLocationManager() != null) { //Stop updates from GPS
            gps.getLocationManager().removeUpdates(gps.getLocationListener());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gps != null) {
            gps.getLocationUpdates(); //Start locationUpdates again from GPS
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (gps != null && requestCode == gps.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gps.getLocationUpdates();
            } else {
                Log.d("location", "onRequestPermissionsResult NOT granted!");
            }
        }
    }

    @Override
    public void gpsLocationChanged(Location location) {
        repository.loadPlaces(location);
    }
}

