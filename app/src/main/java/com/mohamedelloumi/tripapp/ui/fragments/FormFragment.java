package com.mohamedelloumi.tripapp.ui.fragments;


import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mohamedelloumi.tripapp.R;
import com.mohamedelloumi.tripapp.databinding.FragmentFormBinding;
import com.mohamedelloumi.tripapp.presenters.FormPresenter;
import com.mohamedelloumi.tripapp.utils.ConnectivityStatusWorker;
import com.mohamedelloumi.tripapp.db.TripRoomDatabase;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment implements FormPresenter.RequiredViewOps {


    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFormBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_form, container, false);
        TripRoomDatabase db = TripRoomDatabase.getDatabase(getContext().getApplicationContext());
        binding.setPresenter(new FormPresenter(db, this));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        startConnectivityWorker();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Show a short toast
     * @param msg messgae to show within Toast
     */
    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Initialize a PeriodicWorkRequest using the ConnectivityStatusWorker class and start this request
     */
    private void startConnectivityWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(ConnectivityStatusWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(getContext().getApplicationContext())
                .enqueue(saveRequest);
    }
}
