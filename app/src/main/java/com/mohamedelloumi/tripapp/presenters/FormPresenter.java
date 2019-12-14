package com.mohamedelloumi.tripapp.presenters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.mohamedelloumi.tripapp.logic.CitiesLogic;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class FormPresenter implements CitiesLogic.CitiesInterface {

    public final ObservableArrayList<String> citiesList = new ObservableArrayList<>();
    public final ObservableField<String> departureAddress = new ObservableField<>();
    public ObservableField<String> departureDate = new ObservableField<>();
    public final ObservableField<String> departureTime = new ObservableField<>();
    public final ObservableField<String> arrivalAddress = new ObservableField<>();
    public final ObservableField<String> arrivalDate = new ObservableField<>();
    public final ObservableField<String> arrivalTime = new ObservableField<>();

    public FormPresenter() {
        CitiesLogic citiesLogic = new CitiesLogic(this);
        citiesLogic.returnGermanCities();
    }

    /**
     * check if the filled form is valid or no therefore check if departure and arrival address are valid
     * also check that both departure date and departure time are valid
     *
     * @return
     */
    private boolean isValidForm() {
        return true;
    }

    public void saveTripData() {
        if (isValidForm()) {
            System.out.println(departureAddress.get());
            System.out.println(arrivalAddress.get());
            System.out.println(departureDate.get());
            System.out.println(departureTime.get());
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
        System.out.println(Arrays.toString(t.getStackTrace()));
    }
}
