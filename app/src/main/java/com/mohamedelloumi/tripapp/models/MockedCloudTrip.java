package com.mohamedelloumi.tripapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * The following class act as a cloud SQL table
 */
@Entity(tableName = "mocked_cloud_trip_table")
public class MockedCloudTrip {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "departureAddress")
    private String departureAddress;

    @NonNull
    @ColumnInfo(name = "departureDate")
    private String departureDate;

    @NonNull
    @ColumnInfo(name = "departureTime")
    private String departureTime;

    @NonNull
    @ColumnInfo(name = "arrivalAddress")
    private String arrivalAddress;

    @NonNull
    @ColumnInfo(name = "arrivalDate")
    private String arrivalDate;

    @NonNull
    @ColumnInfo(name = "arrivalTime")
    private String arrivalTime;

    public MockedCloudTrip(int id, @NonNull String departureAddress, @NonNull String departureDate, @NonNull String departureTime, @NonNull String arrivalAddress, @NonNull String arrivalDate, @NonNull String arrivalTime) {
        this.id = id;
        this.departureAddress = departureAddress;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalAddress = arrivalAddress;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getDepartureAddress() {
        return departureAddress;
    }

    @NonNull
    public String getDepartureDate() {
        return departureDate;
    }

    @NonNull
    public String getDepartureTime() {
        return departureTime;
    }

    @NonNull
    public String getArrivalAddress() {
        return arrivalAddress;
    }

    @NonNull
    public String getArrivalDate() {
        return arrivalDate;
    }

    @NonNull
    public String getArrivalTime() {
        return arrivalTime;
    }
}
