package com.mohamedelloumi.tripapp.logic;

import com.mohamedelloumi.tripapp.network.CitiesService;
import com.mohamedelloumi.tripapp.network.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class CitiesLogic {
    private CitiesService citiesService = Client.getRetrofit().create(CitiesService.class);

    private CitiesInterface citiesInterface;

    public CitiesLogic(CitiesInterface citiesInterface) {
        this.citiesInterface = citiesInterface;
    }

    public void returnGermanCities() {
        Call<ArrayList<String>> call = citiesService.getCities();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                citiesInterface.onCitiesResponse(response);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                citiesInterface.onCitiesFailure(t);

            }
        });

    }

    public interface CitiesInterface {
        void onCitiesResponse(Response<ArrayList<String>> response);

        void onCitiesFailure(Throwable t);
    }
}
