package com.example.placestogo.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = Visited.class, exportSchema = false)
public abstract class VisitedDatabase extends RoomDatabase {

//    private static VisitedDatabase INSTANCE;

    public abstract VisitedDao getVisitedDao();
}
