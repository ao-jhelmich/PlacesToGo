package com.example.placestogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.placestogo.domain.Place;
import com.example.placestogo.domain.places.PlaceRepository;
import com.example.placestogo.domain.places.PlacesAdapter;

public class MainActivity extends AppCompatActivity {
    protected PlaceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.repository = new PlaceRepository(getApplicationContext());

        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(new PlacesAdapter(repository.getPlaces()));
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        Place place = this.repository.getByPos(pos);

        Toast.makeText(getApplicationContext(), "Clicked on: " + place.getName(), Toast.LENGTH_SHORT).show();
    }
}

