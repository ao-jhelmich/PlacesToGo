package com.example.placestogo.persistence;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitedRepository {

    private VisitedDao mVisitedDao;
    private LiveData<List<Visited>> mVisitedList;

    VisitedRepository(Application application){
        VisitedDatabase db = VisitedDatabase.getDatabase(application);
        mVisitedDao = db.getVisitedDao();
        mVisitedList = mVisitedDao.getLiveVisited();
    }

    public void insert(Visited visited){
        new insertAsyncTask(mVisitedDao).execute(visited);
    }


    private class insertAsyncTask extends AsyncTask<Visited, Void, Void> {

        private VisitedDao asyncTaskDao;

        insertAsyncTask(VisitedDao mVisitedDao) {
            asyncTaskDao = mVisitedDao;
        }

        @Override
        protected Void doInBackground(final Visited... visiteds) {
            asyncTaskDao.insert(visiteds[0]);
            return null;
        }
    }
}
