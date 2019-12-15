package com.mohamedelloumi.tripapp.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mohamedelloumi.tripapp.dao.TripDao;
import com.mohamedelloumi.tripapp.models.Trip;

@Database(entities = {Trip.class}, version = 1, exportSchema = false)
public abstract class TripRoomDatabase extends RoomDatabase {
    public abstract TripDao tripDao();

    private static TripRoomDatabase INSTANCE;

    public static TripRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TripRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripRoomDatabase.class, "trip_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
