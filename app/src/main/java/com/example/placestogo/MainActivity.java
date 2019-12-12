package com.example.placestogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.placestogo.domain.places.Place;
import com.example.placestogo.domain.places.PlaceRepository;
import com.example.placestogo.domain.places.PlacesAdapter;

public class MainActivity extends AppCompatActivity {
    protected PlaceRepository repository;
    protected PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.repository = new PlaceRepository(this);
        this.adapter = new PlacesAdapter(repository.getPlaces());

        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(this.adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        Place place = this.repository.getByPos(pos);

        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("placeName", place);
        startActivity(intent);
    }

    public PlaceRepository getRepository() {
        return this.repository;
    }

    public PlacesAdapter getAdapter() {
        return this.adapter;
    }
}

