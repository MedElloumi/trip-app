package com.mohamedelloumi.tripapp.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CitiesService {
    @GET("cities")
    Call<ArrayList<String>> getCities();
}
