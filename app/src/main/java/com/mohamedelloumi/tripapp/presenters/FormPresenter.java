package com.mohamedelloumi.tripapp.presenters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.mohamedelloumi.tripapp.logic.CitiesLogic;
import com.mohamedelloumi.tripapp.logic.TripLogic;
import com.mohamedelloumi.tripapp.models.Trip;
import com.mohamedelloumi.tripapp.utils.TripRoomDatabase;
import com.mohamedelloumi.tripapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

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
    private RequiredViewOps requiredViewOps;

    public FormPresenter(TripRoomDatabase db, RequiredViewOps requiredViewOps) {
        this.db = db;
        this.requiredViewOps = requiredViewOps;
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
            requiredViewOps.showToast("Invalid user input");
        }
    }

    /**
     * Interface callback in case that our HTTP request to get German cities succeed (status code)
     * Also need to make sure that the response body is not null and the array is not empty else fetch the data from the local JSON file
     *
     * @param response
     */
    @Override
    public void onCitiesResponse(Response<ArrayList<String>> response) {
        if ((response.body() != null) && (response.body().size() > 0)) {
            citiesList.addAll(response.body());
        } else onCitiesFailure(new Throwable());
    }

    /**
     * Interface callback in case that our HTTP request to get German cities list failed for any reason
     * As the task suggest that we might save offline the trip information, it might happens that the user is offline
     * while filling this form, therefore I provided a second German cities list source which is a local JSON file
     *
     * @param t
     */
    @Override
    public void onCitiesFailure(Throwable t) {
        requiredViewOps.showToast(t.toString());
        JSONArray localCities = Utils.convertStringIntoJSONArray(Utils.loadJSONFromAsset("cities.json"));
        if (localCities != null) {
            for (int i = 0; i < localCities.length(); i++) {
                try {
                    citiesList.add(localCities.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onInsertTripResponse() {
        requiredViewOps.showToast("Insert trip into local storage succeed");
    }

    @Override
    public void onInsertTripFailure() {
        requiredViewOps.showToast("Insert trip into local storage failed");
    }

    public interface RequiredViewOps {
        void showToast(String msg);
    }
}
