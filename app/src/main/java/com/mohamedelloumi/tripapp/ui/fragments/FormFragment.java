package com.mohamedelloumi.tripapp.ui.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mohamedelloumi.tripapp.R;
import com.mohamedelloumi.tripapp.databinding.FragmentFormBinding;
import com.mohamedelloumi.tripapp.presenters.FormPresenter;
import com.mohamedelloumi.tripapp.utils.TripRoomDatabase;

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
    public void showToast(String msg) {
        Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
