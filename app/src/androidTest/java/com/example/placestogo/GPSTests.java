package com.example.placestogo;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GPSTests {

    @Test
    public void gps_isEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals(PackageManager.PERMISSION_GRANTED, ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION));
    }
}
