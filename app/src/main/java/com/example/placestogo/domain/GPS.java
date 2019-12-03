package com.example.placestogo.domain;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class GPS {
    private LocationManager locationManager;
    private CurrentLocation currentLocation;
    private Context mContext;

    public GPS(Context context) {
        currentLocation = new CurrentLocation();
        mContext = context;

        //Set Location manager
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        //Check permission
        if (checkGPSPermission() && currentLocation != null) {

            //Set currentLocation to last known location
            if (currentLocation.getLocation() == null) {
                currentLocation.onLocationChanged(Objects.requireNonNull(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)));
            }

            //Request location updates for future location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, currentLocation);
        }
    }

    public boolean checkGPSPermission() {
        PackageManager pm = mContext.getPackageManager();
        boolean GPSPermission = mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && pm.hasSystemFeature(PackageManager.FEATURE_LOCATION)
                                && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSPermission) {
            //TODO: https://stackoverflow.com/questions/29801368/how-to-show-enable-location-dialog-like-google-maps

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in the settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        return GPSPermission;
    }

    public CurrentLocation getLocation() {
        if (currentLocation != null && currentLocation.getLocation() != null) {
            return currentLocation;
        } else {
            return null;
        }
    }
}
