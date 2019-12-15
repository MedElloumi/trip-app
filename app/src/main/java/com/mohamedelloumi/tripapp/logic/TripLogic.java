package com.mohamedelloumi.tripapp.logic;

import android.content.Context;
import android.os.AsyncTask;

import com.mohamedelloumi.tripapp.dao.TripDao;
import com.mohamedelloumi.tripapp.models.Trip;
import com.mohamedelloumi.tripapp.utils.TripRoomDatabase;

import java.util.Arrays;

public class TripLogic {

    private TripInterface tripInterface;

    public TripLogic(TripInterface tripInterface) {
        this.tripInterface = tripInterface;
    }

    public void insertTrip(Trip trip, TripRoomDatabase db) {
        // TripRoomDatabase db = TripRoomDatabase.getDatabase(application);
        TripDao tripDao = db.tripDao();
        new insertTripAsyncTask(tripDao).execute(trip);
    }

    private class insertTripAsyncTask extends AsyncTask<Trip, Void, Boolean> {

        private TripDao tripDao;

        private insertTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                tripInterface.onInsertTripResponse();
            } else {
                tripInterface.onInsertTripFailure();
            }
        }

        @Override
        protected final Boolean doInBackground(Trip... lists) {
            try {
                tripDao.insertTrip(lists[0]);
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
    }

    public interface TripInterface {
        void onInsertTripResponse();

        void onInsertTripFailure();
    }
}
