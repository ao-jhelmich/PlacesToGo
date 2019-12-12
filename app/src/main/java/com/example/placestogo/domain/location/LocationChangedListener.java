package com.example.placestogo.domain.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public interface LocationChangedListener extends LocationListener {
    @Override
    void onLocationChanged(Location location);

    @Override
    void onStatusChanged(String s, int i, Bundle bundle);

    @Override
    default void onProviderEnabled(String s) {}

    @Override
    default void onProviderDisabled(String s) {}
}
