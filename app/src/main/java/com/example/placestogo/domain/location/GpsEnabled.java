package com.example.placestogo.domain.location;

import android.location.Location;

public interface GpsEnabled {
    void gpsLocationChanged(Location location);
}
