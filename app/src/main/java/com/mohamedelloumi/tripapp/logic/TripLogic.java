package com.mohamedelloumi.tripapp.logic;

import android.os.AsyncTask;

import com.mohamedelloumi.tripapp.dao.TripDao;
import com.mohamedelloumi.tripapp.models.MockedCloudTrip;
import com.mohamedelloumi.tripapp.models.Trip;
import com.mohamedelloumi.tripapp.db.TripRoomDatabase;

import java.util.List;

public class TripLogic {

    private static TripInterface tripInterface;

    public TripLogic(TripInterface tripInterface) {
        TripLogic.tripInterface = tripInterface;
    }

    public void insertTrip(Trip trip, TripRoomDatabase db) {
        TripDao tripDao = db.tripDao();
        new insertTripAsyncTask(tripDao).execute(trip);
    }

    public void insertCloudTrip(MockedCloudTrip trip, TripRoomDatabase db) {
        TripDao tripDao = db.tripDao();
        new insertCloudTripAsyncTask(tripDao).execute(trip);
    }

    public void selectLocalTrips(TripRoomDatabase db) {
        TripDao tripDao = db.tripDao();
        new selectLocalTripsAsyncTask(tripDao).execute();
    }

    public void selectRemoteTrips(TripRoomDatabase db) {
        TripDao tripDao = db.tripDao();
        new selectRemoteTripsAsyncTask(tripDao).execute();
    }

    private static class insertTripAsyncTask extends AsyncTask<Trip, Void, Boolean> {

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

    private static class selectLocalTripsAsyncTask extends AsyncTask<Void, Void, List<Trip>> {

        private TripDao tripDao;

        private selectLocalTripsAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected void onPostExecute(List<Trip> localTrips) {
            tripInterface.onSelectLocalTrips(localTrips);
            super.onPostExecute(localTrips);
        }

        @Override
        protected List<Trip> doInBackground(Void... voids) {
            return tripDao.selectLocalTrips();
        }
    }

    private static class selectRemoteTripsAsyncTask extends AsyncTask<Void, Void, List<MockedCloudTrip>> {

        private TripDao tripDao;

        private selectRemoteTripsAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected void onPostExecute(List<MockedCloudTrip> remoteTrips) {
            tripInterface.onSelectRemoteTrips(remoteTrips);
            super.onPostExecute(remoteTrips);
        }

        @Override
        protected List<MockedCloudTrip> doInBackground(Void... voids) {
            return tripDao.selectRemoteTrips();
        }
    }

    private static class insertCloudTripAsyncTask extends AsyncTask<MockedCloudTrip, Void, Boolean> {

        private TripDao tripDao;

        private insertCloudTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                tripInterface.onInsertCloudTripResponse();
            } else {
                tripInterface.onInsertCloudTripFailure();
            }
        }

        @Override
        protected final Boolean doInBackground(MockedCloudTrip... lists) {
            try {
                tripDao.insertCloudTrip(lists[0]);
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

        void onSelectLocalTrips(List<Trip> localTrips);

        void onSelectRemoteTrips(List<MockedCloudTrip> cloudTrips);

        void onInsertCloudTripResponse();

        void onInsertCloudTripFailure();
    }
}
