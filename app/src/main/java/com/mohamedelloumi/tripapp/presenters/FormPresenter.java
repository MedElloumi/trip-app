package com.mohamedelloumi.tripapp.presenters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.mohamedelloumi.tripapp.logic.CitiesLogic;
import com.mohamedelloumi.tripapp.logic.TripLogic;
import com.mohamedelloumi.tripapp.models.Trip;
import com.mohamedelloumi.tripapp.utils.TripRoomDatabase;

import java.util.ArrayList;

import retrofit2.Response;

public class FormPresenter implements CitiesLogic.CitiesInterface, TripLogic.TripInterface {

    public final ObservableArrayList<String> citiesList = new ObservableArrayList<>();
    public final ObservableField<String> departureAddress = new ObservableField<>();
    public final ObservableField<String> departureDate = new ObservableField<>();
    public final ObservableField<String> departureTime = new ObservableField<>();
    public final ObservableField<String> arrivalAddress = new ObservableField<>();
    public final ObservableField<String> arrivalDate = new ObservableField<>();
    public final ObservableField<String> arrivalTime = new ObservableField<>();

    private TripRoomDatabase db;

    public FormPresenter(TripRoomDatabase db) {
        this.db = db;
        CitiesLogic citiesLogic = new CitiesLogic(this);
        citiesLogic.returnGermanCities();
    }

    /**
     * Check if the filled form is valid or no therefore check that all attributes are not Null
     * also make sure that that departure and arrival address are included within the cities list
     *
     * @return
     */
    private boolean isValidForm() {
        boolean noNullDepartureAttribute = (departureAddress.get() != null) && (departureDate.get() != null) && (departureTime.get() != null);
        boolean noNullArrivalAttribute = (arrivalAddress.get() != null) && (arrivalDate.get() != null) && (arrivalTime.get() != null);
        boolean isValidAddress = citiesList.contains(departureAddress.get()) && citiesList.contains(arrivalAddress.get());
        return noNullDepartureAttribute && noNullArrivalAttribute && isValidAddress;
    }

    public void saveTripData() {
        if (isValidForm()) {
            TripLogic tripLogic = new TripLogic(this);
            Trip trip = new Trip(1, departureAddress.get(), departureDate.get(), departureTime.get(), arrivalAddress.get(), arrivalDate.get(), arrivalTime.get());
            tripLogic.insertTrip(trip, db);
        } else {
            System.out.println("invalid user input");
        }
    }

    @Override
    public void onCitiesResponse(Response<ArrayList<String>> response) {
        if (response.body() != null) {
            citiesList.addAll(response.body());
        }

    }

    @Override
    public void onCitiesFailure(Throwable t) {
        System.out.println(t.toString());
    }

    @Override
    public void onInsertTripResponse() {
        System.out.println("insert trip success");
    }

    @Override
    public void onInsertTripFailure() {
        System.out.println("insert trip failure");
    }
}
