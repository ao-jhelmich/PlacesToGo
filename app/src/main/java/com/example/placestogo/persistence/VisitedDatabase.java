package com.example.placestogo.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = Visited.class, exportSchema = false)
public abstract class VisitedDatabase extends RoomDatabase {

    private static VisitedDatabase INSTANCE;

    public abstract VisitedDao getVisitedDao();

    public static VisitedDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (VisitedDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VisitedDatabase.class, "db_visited")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
