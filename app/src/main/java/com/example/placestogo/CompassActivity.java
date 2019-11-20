package com.example.placestogo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placestogo.domain.GPS;

public class CompassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        Log.i("compass-enabled", "Compass enabled: " + hasCompassEnabled());

        GPS gps = new GPS(this);
        if (gps.getLocation() != null) {
            Log.i("location", gps.getLocation().toString());
        } else {
            Log.i("location", "null");
        }
    }

    private boolean hasCompassEnabled() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
    }
}
