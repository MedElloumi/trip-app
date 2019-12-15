package com.mohamedelloumi.tripapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.mohamedelloumi.tripapp.models.Trip;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(Trip trip);
}
