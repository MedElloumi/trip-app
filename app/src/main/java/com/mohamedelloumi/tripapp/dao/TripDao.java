package com.mohamedelloumi.tripapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mohamedelloumi.tripapp.models.MockedCloudTrip;
import com.mohamedelloumi.tripapp.models.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(Trip trip);

    @Query("SELECT * FROM trip_table")
    List<Trip> selectLocalTrips();

    @Query("SELECT * FROM mocked_cloud_trip_table")
    List<MockedCloudTrip> selectRemoteTrips();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCloudTrip(MockedCloudTrip trip);
}
