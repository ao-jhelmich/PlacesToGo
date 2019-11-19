package com.example.placestogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlaceRepository repository = new PlaceRepository();
        RecyclerView rvPlaces = findViewById(R.id.rvPlaces);

        rvPlaces.setAdapter(new PlacesAdapter(repository.getPlaces()));
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    public void userItemClick(int pos) {
        Log.i("Itemclick", "Clicked on: " + pos);
    }
}

