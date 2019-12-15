package com.mohamedelloumi.tripapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ConnectivityStatusWorker extends Worker {

    public ConnectivityStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (isConnected()) {
            System.out.println("internet connection working");
            EventBus.getDefault().post(true);
        } else {
            System.out.println("internet connection not working");
            EventBus.getDefault().post(false);
        }
        return null;
    }

    /**
     * check if we are connected to an Internet connection or no
     * copy pasted from here: https://developer.android.com/training/monitoring-device-state/connectivity-status-type#java
     * @return
     */
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) ApplicationContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return ((activeNetwork != null) && (activeNetwork.isConnected()));
    }
}
