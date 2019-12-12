package com.example.placestogo;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.placestogo.domain.location.GPS;
import com.example.placestogo.domain.location.GpsEnabled;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, GpsEnabled {
    private ImageView compassImage;

    private GPS gps;
    private Location currentLocation;
    private Location destination;

    private SensorManager sensorManager;

    private float lastDegree = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_compass);

        compassImage = findViewById(R.id.imageViewCompass);

        gps = new GPS(this); //Initiate GPS

        destination = new Location(""); //TODO: Replace this with correct location/Place object
        destination.setLatitude(51.5852493);
        destination.setLongitude(4.7772573);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        if (gps != null) {
            gps.getLocationUpdates(); //Start locationUpdates again from GPS
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); //Stop updates from sensor

        if (gps != null && gps.getLocationManager() != null) { //Stop updates from GPS
            gps.getLocationManager().removeUpdates(gps.getLocationListener());
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (currentLocation == null || destination == null) return; //Need location and destination

        float degree = calculateDegree(event);

        float difference = lastDegree - degree;

        if (difference > 2) {
            Toast.makeText(this, "Degree: " + degree, Toast.LENGTH_SHORT).show();

            rotateImage(lastDegree, degree);
        }

        lastDegree = degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private float calculateDegree(SensorEvent event) {
        float azimuth = event.values[0];

        GeomagneticField geoField = new GeomagneticField(
                Double.valueOf(currentLocation.getLatitude()).floatValue(),
                Double.valueOf(currentLocation.getLongitude()).floatValue(),
                Double.valueOf(currentLocation.getAltitude()).floatValue(),
                System.currentTimeMillis()
        );

        azimuth -= geoField.getDeclination(); //Converts magnetic north into true north

        float bearTo = currentLocation.bearingTo(destination); //Bearing towards the destination

        //If the bearTo is smaller than 0, add 360 to get the rotation clockwise.
        if (bearTo < 0) {
            bearTo = bearTo + 360;
        }

        float degree = bearTo - azimuth;

        //If the direction is smaller than 0, add 360 to get the rotation clockwise.
        if (degree < 0) {
            degree = degree + 360;
        }

        return degree;
    }

    private void rotateImage(float from, float to) {
        RotateAnimation animation = new RotateAnimation(
                from,
                to,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        animation.setInterpolator(new LinearInterpolator()); //Smooth animation
        animation.setDuration(500); //Duration of animation
        animation.setFillAfter(true); //Set the animation after the end of the reservation status

        compassImage.startAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (gps != null && requestCode == gps.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gps.getLocationUpdates();
            } else {
                Log.d("location", "onRequestPermissionsResult NOT granted!");
            }
        }
    }

    @Override
    public void gpsLocationChanged(Location location) { //Gets called when location changes in GPS
        currentLocation = location;
        //Toast.makeText(this, "Meters to location: " + currentLocation.distanceTo(destination), Toast.LENGTH_SHORT).show();
    }
}
