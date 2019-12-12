package com.example.placestogo.domain.location;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GPS {
    public final int REQUEST_CODE = 1; //Request code, should be unique
    private final long MIN_TIME = 5000; //Every 5 seconds
    private final float MIN_DISTANCE = 5; //Every 5 meters

    private final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private GpsEnabled gpsEnabled;
    private Context context;

    public GPS(Context context) {
        this.gpsEnabled = (GpsEnabled) context;
        this.context = context;
        setupLocationServices();
        getLocationUpdates();
    }

    private void setupLocationServices() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationChangedListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    gpsEnabled.gpsLocationChanged(location);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                String statusTxt = "";
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        statusTxt = "OUT_OF_SERVICE";
                        break;
                    case LocationProvider.AVAILABLE:
                        statusTxt = "AVAILABLE";
                        break;
                    // setting the minTime when requesting location updates will cause the provider to set itself to TEMPORARILY_UNAVAILABLE for minTime milliseconds in order to conserve battery power.
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        statusTxt = "TEMPORARILY_UNAVAILABLE";
                        break;

                }
                Log.d("status", statusTxt);
            }
        };
    }

    public void getLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        locationListener.onLocationChanged(locationManager.getLastKnownLocation(LOCATION_PROVIDER));
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public LocationListener getLocationListener() {
        return locationListener;
    }
}