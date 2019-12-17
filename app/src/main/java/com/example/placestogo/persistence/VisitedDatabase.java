package com.example.placestogo.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(version = 2, entities = Visited.class, exportSchema = false)
public abstract class VisitedDatabase extends RoomDatabase {

    private static VisitedDatabase INSTANCE;

    public abstract VisitedDao getVisitedDao();

    public static VisitedDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (VisitedDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VisitedDatabase.class, "db_visited")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE db_visited "
                    + " ADD COLUMN uniqueId TEXT");
        }

    };
}
