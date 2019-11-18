package com.example.placestogo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.placestogo.domain.GoogleApi;
import com.google.android.libraries.places.api.Places;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        new GoogleApi(getApplicationContext()).getPlaces();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
