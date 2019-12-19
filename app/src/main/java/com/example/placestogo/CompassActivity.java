package com.example.placestogo;

import android.annotation.SuppressLint;
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
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.placestogo.domain.location.GPS;
import com.example.placestogo.domain.location.GpsEnabled;
import com.example.placestogo.domain.places.Place;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, GpsEnabled {
    private ImageView compassImage;
    private TextView tvDestination;
    private TextView tvDestinationDist;

    private GPS gps;
    private Location currentLocation;
    private Place place;
    private Location destination;

    private SensorManager sensorManager;

    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];

    private float currentDegree = 0f;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_compass);

        compassImage = findViewById(R.id.imageViewCompass);
        tvDestination = findViewById(R.id.tvDestination);
        tvDestinationDist = findViewById(R.id.tvDestinationDist);

        gps = new GPS(this); //Initiate GPS

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            Place place = (Place) extra.get("place");
            if (place != null) {
                this.place = place;
                destination = place.getLocation();

                tvDestination.setText("Destination: " + place.getName());
            }
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);

        if (gps != null) {
            gps.getLocationUpdates(); //Start locationUpdates again from GPS
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, mAccelerometer);
        sensorManager.unregisterListener(this, mMagnetometer);

        if (gps != null && gps.getLocationManager() != null) { //Stop updates from GPS
            gps.getLocationManager().removeUpdates(gps.getLocationListener());
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (currentLocation == null || destination == null) return; //Need location and destination

        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }

        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);

            float azimuthInDegrees = calculateDegree();

            rotateImage(currentDegree, -azimuthInDegrees);

            currentDegree = -azimuthInDegrees;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private float calculateDegree() {
        float azimuthInRadians = mOrientation[0];
        float azimuthInDegrees = (float)(Math.round(Math.toDegrees(azimuthInRadians))+360)%360;

        azimuthInDegrees = offsetDegree(azimuthInDegrees);

        if (currentDegree != -azimuthInDegrees) {
            Toast.makeText(this, "Degree: " + azimuthInDegrees, Toast.LENGTH_SHORT).show();
        }

        return azimuthInDegrees;
    }

    private float offsetDegree(float degree) {
        GeomagneticField geoField = new GeomagneticField(
                (float) currentLocation.getLatitude(),
                (float) currentLocation.getLongitude(),
                (float) currentLocation.getAltitude(),
                System.currentTimeMillis());

        degree += geoField.getDeclination(); //Converts magnetic north to true north

        float bearing = Math.round(currentLocation.bearingTo(destination)); //bearingTo returns degrees

        return degree - bearing;
    }

    private void rotateImage(float from, float to) {
        RotateAnimation animation = new RotateAnimation(
                from,
                to,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        animation.setDuration(250); //Duration of animation
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

    @SuppressLint("SetTextI18n")
    @Override
    public void gpsLocationChanged(Location location) { //Gets called when location changes in GPS
        currentLocation = location;

        if (destination != null) {
            tvDestinationDist.setText(Math.round(currentLocation.distanceTo(destination)) + " meters till destination.");
        }
    }
}
