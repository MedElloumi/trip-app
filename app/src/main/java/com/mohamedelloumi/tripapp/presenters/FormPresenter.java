package com.mohamedelloumi.tripapp.presenters;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import com.mohamedelloumi.tripapp.logic.CitiesLogic;
import com.mohamedelloumi.tripapp.logic.TripLogic;
import com.mohamedelloumi.tripapp.models.MockedCloudTrip;
import com.mohamedelloumi.tripapp.models.Trip;
import com.mohamedelloumi.tripapp.db.TripRoomDatabase;
import com.mohamedelloumi.tripapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
    private Trip localTrip;

    public FormPresenter(TripRoomDatabase db, RequiredViewOps requiredViewOps) {
        this.db = db;
        this.requiredViewOps = requiredViewOps;
        EventBus.getDefault().register(this);
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

    /**
     * This method will be called once the user click save the trip button
     * Therefore check if the form is valid and call the required Room method to insert this trip in SQLite
     * Results from this SQLite operation will be fired on onInsertTripResponse and onInsertTripFailure
     */
    public void saveTripData() {
        if (isValidForm()) {
            TripLogic tripLogic = new TripLogic(this);
            Trip trip = new Trip( departureAddress.get(), departureDate.get(), departureTime.get(), arrivalAddress.get(), arrivalDate.get(), arrivalTime.get());
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
        // requiredViewOps.showToast(t.toString());
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

    /**
     * Interface callback in case our Room operation to insert a trip succeed
     * Therefore show a Toast as an indication
     */
    @Override
    public void onInsertTripResponse() {
        requiredViewOps.showToast("Insert trip into local storage succeed");
    }

    /**
     * Interface callback in case our Room operation to insert a trip failed
     * Therefore show a Toast as an indication
     */
    @Override
    public void onInsertTripFailure() {
        requiredViewOps.showToast("Insert trip into local storage failed");
    }

    /**
     * Interface callback fired after selecting local trips the one saved in SQLight
     * If the size of the saved local trips equal 1 that means that we might select data from the cloud and compare the difference for possible synchronisation.
     * If the size of the saved local trips equal 0 abort and no need to synchronise.
     * If the size of saved local trips > 1 do nothing, this should not be possible.
     *
     * @param localTrips
     */
    @Override
    public void onSelectLocalTrips(List<Trip> localTrips) {
        if (localTrips.size() == 0) {
            requiredViewOps.showToast("Empty local trips saved one the phone ... aborting synchronisation process");
        } else if (localTrips.size() == 1) {
            requiredViewOps.showToast("found 1 local trip saved one the phone ... continuing synchronisation process");
            localTrip = localTrips.get(0);
            TripLogic tripLogic = new TripLogic(this);
            tripLogic.selectRemoteTrips(db);
        } else {
            requiredViewOps.showToast("Wrong number of trips saved one the phone ... aborting synchronisation process");
        }

    }

    /**
     * Interface callback fired after selecting remote trips (supposedly from the cloud).
     * If the number of remote trips equal 0 that means the cloud have no information about our local saved trips therefore push our saved local trip into the cloud.
     * Else if the number of remote trips is more or equal than 1 then we will need to check if our local trip has been synchronised before or no.
     *
     * @param cloudTrips
     */
    @Override
    public void onSelectRemoteTrips(List<MockedCloudTrip> cloudTrips) {
        TripLogic tripLogic = new TripLogic(this);
        if (cloudTrips.size() == 0) {
            requiredViewOps.showToast("Empty cloud data ... continuing synchronisation process");
            MockedCloudTrip trip = new MockedCloudTrip(localTrip.getDepartureAddress2(), localTrip.getDepartureDate2(), localTrip.getDepartureTime2(), localTrip.getArrivalAddress2(), localTrip.getArrivalDate2(), localTrip.getArrivalTime2());
            tripLogic.insertCloudTrip(trip, db);

        } else if (cloudTrips.size() >= 1) {
            boolean isLocalTripSynchronised = false;
            for (int i = 0; i < cloudTrips.size(); i++) {
                if (cloudTrips.get(i).getArrivalAddress2().equals(localTrip.getArrivalAddress2())) {
                    isLocalTripSynchronised = true;
                }
            }
            if (!isLocalTripSynchronised) {
                requiredViewOps.showToast("Inserting new trip information ...");
                MockedCloudTrip trip = new MockedCloudTrip(localTrip.getDepartureAddress2(), localTrip.getDepartureDate2(), localTrip.getDepartureTime2(), localTrip.getArrivalAddress2(), localTrip.getArrivalDate2(), localTrip.getArrivalTime2());
                tripLogic.insertCloudTrip(trip, db);
            } else {
                requiredViewOps.showToast("Trip already synchronized ... aborting synchronisation process");
            }
        }

    }

    /**
     * Interface callback in case our operation to insert the trip into cloud succeed
     * Therefore show a Toast as indication
     */
    @Override
    public void onInsertCloudTripResponse() {
        requiredViewOps.showToast("Insert trip into cloud storage succeed");
    }

    /**
     * Interface callback in case our operation to insert the trip into cloud failed
     * Therefore show a Toast as indication
     */
    @Override
    public void onInsertCloudTripFailure() {
        requiredViewOps.showToast("Insert trip into cloud storage failed");
    }

    public interface RequiredViewOps {
        void showToast(String msg);
    }

    /**
     * EventBus callback fired after receiving the connection status
     * If the user is connected then we might pass to the first step of the synchronisation process which is selecting local saved trips
     * If the user is not connected then just show a Toast
     *
     * @param isConnected
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void connectionStatusEvent(Boolean isConnected) {
        System.out.println("winston logs connectionStatusEvent EventBus");
        if (isConnected) {
            TripLogic tripLogic = new TripLogic(this);
            tripLogic.selectLocalTrips(db);
        } else {
            requiredViewOps.showToast("User not connect to network ... aborting synchronisation process");
        }
    }

}
