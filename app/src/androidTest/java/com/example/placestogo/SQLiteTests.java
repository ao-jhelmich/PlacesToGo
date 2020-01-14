package com.example.placestogo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SQLiteTests {
    private Place place = new Place("246OM12690WRY9M2MGHSF0246N80SFH0M2M21MLLSFH04262", "Test","url",0.0,0.0);

    @Test
    public void place_isInserted() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        VisitedDatabase db = Room.databaseBuilder(context, VisitedDatabase.class, "db_visited")
                .allowMainThreadQueries()
                .build();

        Visited visited = new Visited();
        visited.setPlaceId(place.getId());

        VisitedDao visitedDao = db.getVisitedDao();
        visitedDao.insert(visited);

        assertTrue(db.checkPlaceVisited(context, place.getId()));
    }
}
