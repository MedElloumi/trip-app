package com.mohamedelloumi.tripapp.presenters;

import android.databinding.ObservableArrayList;

import com.mohamedelloumi.tripapp.logic.CitiesLogic;

import java.util.ArrayList;

import retrofit2.Response;

public class FormPresenter implements CitiesLogic.CitiesInterface {
    // citiesList will contains German cities after consuming the Rest API, I will use this list as an input for both departure and arrival address
    public ObservableArrayList<String> citiesList = new ObservableArrayList<>();

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
    private boolean isValid() {
        return false;
    }

    @Override
    public void onCitiesResponse(Response<ArrayList<String>> response) {
        if (response.body() != null) {
            citiesList.addAll(response.body());
        }

    }

    @Override
    public void onCitiesFailure(Throwable t) {
    }
}
