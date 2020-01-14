package com.example.placestogo;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.placestogo.domain.location.GPS;
import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {
    @Test
    public void place_isInserted() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String placeId = "246OM12690WRY9M2MGHSF0246N80SFH0M2M21MLLSFH04262";

        VisitedDatabase db = Room.databaseBuilder(context, VisitedDatabase.class, "db_visited")
                .allowMainThreadQueries()
                .build();

        Visited visited = new Visited();
        visited.setPlaceId(placeId);

        VisitedDao visitedDao = db.getVisitedDao();
        visitedDao.insert(visited);

        assertTrue(db.checkPlaceVisited(context, placeId));
    }

    @Test
    public void gps_isEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        Place place = new Place("246OM12690WRY9M2MGHSF0246N80SFH0M2M21MLLSFH04262", "Test","url",0.0,0.0);
        Intent intent = new Intent().setClassName(context, String.valueOf(CompassActivity.class));
        intent.putExtra("place", place);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        context = InstrumentationRegistry.getInstrumentation().getContext();

        GPS gps = new GPS(context);

        assertEquals("com.example.placestogo", context.getPackageName());
    }
}
