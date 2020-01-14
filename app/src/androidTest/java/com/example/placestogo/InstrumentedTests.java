package com.example.placestogo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

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
}
