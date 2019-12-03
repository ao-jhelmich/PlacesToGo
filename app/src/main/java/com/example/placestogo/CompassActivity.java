package com.example.placestogo;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placestogo.domain.GPS;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView compassImage;
    private GPS gps;

    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private int lastRotation = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_compass);

        compassImage = findViewById(R.id.imageViewCompass);
        gps = new GPS(this);

        //Show location in toast if not null
        if (gps.getLocation() != null) {
            Toast.makeText(getApplicationContext(), gps.getLocation().toString(), Toast.LENGTH_SHORT).show();
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); //Stop updates from sensor
    }

    private boolean hasCompassEnabled() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
    }

    private int rotateImage(int from, int to) {
        RotateAnimation animation = new RotateAnimation(
                from,
                to, //Maybe negative
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        animation.setInterpolator(new LinearInterpolator()); //Smooth animation
        animation.setDuration(200); //Duration of animation
        animation.setFillAfter(true); //Set the animation after the end of the reservation status

        compassImage.startAnimation(animation); //Start animation
        return to;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }

        updateOrientationAngles();
    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        int newRotation = (int) Math.toDegrees(Math.atan2((rotationMatrix[1] - rotationMatrix[3]), (rotationMatrix[0] + rotationMatrix[4])));
        if (newRotation < 0) {
            newRotation += 360;
        }

        Toast.makeText(this, "Rotation: " + newRotation, Toast.LENGTH_SHORT).show();

        lastRotation = rotateImage(lastRotation, newRotation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
