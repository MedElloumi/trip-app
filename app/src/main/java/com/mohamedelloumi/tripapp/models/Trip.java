package com.mohamedelloumi.tripapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "trip_table")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id = 0;

    @NonNull
    @ColumnInfo(name = "departureAddress2")
    private String departureAddress2;

    @NonNull
    @ColumnInfo(name = "departureDate2")
    private String departureDate2;

    @NonNull
    @ColumnInfo(name = "departureTime2")
    private String departureTime2;

    @NonNull
    @ColumnInfo(name = "arrivalAddress2")
    private String arrivalAddress2;

    @NonNull
    @ColumnInfo(name = "arrivalDate2")
    private String arrivalDate2;

    @NonNull
    @ColumnInfo(name = "arrivalTime2")
    private String arrivalTime2;

    public Trip(@NonNull String departureAddress2, @NonNull String departureDate2, @NonNull String departureTime2, @NonNull String arrivalAddress2, @NonNull String arrivalDate2, @NonNull String arrivalTime2) {
        this.departureAddress2 = departureAddress2;
        this.departureDate2 = departureDate2;
        this.departureTime2 = departureTime2;
        this.arrivalAddress2 = arrivalAddress2;
        this.arrivalDate2 = arrivalDate2;
        this.arrivalTime2 = arrivalTime2;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getDepartureAddress2() {
        return departureAddress2;
    }

    @NonNull
    public String getDepartureDate2() {
        return departureDate2;
    }

    @NonNull
    public String getDepartureTime2() {
        return departureTime2;
    }

    @NonNull
    public String getArrivalAddress2() {
        return arrivalAddress2;
    }

    @NonNull
    public String getArrivalDate2() {
        return arrivalDate2;
    }

    @NonNull
    public String getArrivalTime2() {
        return arrivalTime2;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepartureAddress2(@NonNull String departureAddress2) {
        this.departureAddress2 = departureAddress2;
    }

    public void setDepartureDate2(@NonNull String departureDate2) {
        this.departureDate2 = departureDate2;
    }

    public void setDepartureTime2(@NonNull String departureTime2) {
        this.departureTime2 = departureTime2;
    }

    public void setArrivalAddress2(@NonNull String arrivalAddress2) {
        this.arrivalAddress2 = arrivalAddress2;
    }

    public void setArrivalDate2(@NonNull String arrivalDate2) {
        this.arrivalDate2 = arrivalDate2;
    }

    public void setArrivalTime2(@NonNull String arrivalTime2) {
        this.arrivalTime2 = arrivalTime2;
    }
}
