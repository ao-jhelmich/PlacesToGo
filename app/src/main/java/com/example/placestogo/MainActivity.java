package com.example.placestogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.placestogo.domain.Place;
import com.example.placestogo.domain.places.PlaceRepository;
import com.example.placestogo.domain.places.PlacesAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlaceRepository repository = new PlaceRepository(getApplicationContext());
        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(new PlacesAdapter(repository.getPlaces()));
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        PlaceRepository repository = new PlaceRepository(getApplicationContext());
        Place place = repository.getByPos(pos);
        Log.i("Itemclick", "Clicked on: " + pos);
        Log.i("Itemclick", String.valueOf(place));
    }
}

