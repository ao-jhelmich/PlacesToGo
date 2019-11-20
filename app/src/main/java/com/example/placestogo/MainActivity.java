package com.example.placestogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.placestogo.Domain.Place;
import com.example.placestogo.Domain.PlaceRepository;
import com.example.placestogo.Domain.PlacesAdapter;

public class MainActivity extends AppCompatActivity {
    PlaceRepository repository = new PlaceRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(new PlacesAdapter(repository.getPlaces()));
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        Place place = repository.getByPos(pos);
        Log.i("Itemclick", "Clicked on: " + pos);
        Log.i("Itemclick", String.valueOf(place));
    }
}

