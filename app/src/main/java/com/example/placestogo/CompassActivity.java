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

import java.util.Arrays;

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

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
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

    //https://www.javacodegeeks.com/2013/09/android-compass-code-example.html
    //https://stackoverflow.com/questions/39975877/how-to-detect-magnetic-north-azimuth-without-magnetometer
    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        float azimuthInRadians = orientationAngles[0];
        float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);
        if (azimuthInDegrees < 0.0f) {
            azimuthInDegrees += 360f;
        }

        Toast.makeText(this, "Rotation: " + azimuthInDegrees, Toast.LENGTH_LONG).show();
        int newRotation = Math.round(azimuthInDegrees);

        lastRotation = rotateImage(lastRotation, newRotation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
