package com.example.placestogo.domain;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class CurrentLocation implements LocationListener {
    private Location location;

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
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

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        if (location != null) {
            return location.getLatitude() + "," + location.getLongitude();
        } else {
            return "-33.8585416, 151.2100441";
        }
    }
}
