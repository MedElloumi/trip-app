package com.mohamedelloumi.tripapp.ui.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohamedelloumi.tripapp.R;
import com.mohamedelloumi.tripapp.databinding.FragmentFormBinding;
import com.mohamedelloumi.tripapp.presenters.FormPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {


    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFormBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_form, container, false);
        binding.setPresenter(new FormPresenter());
        return binding.getRoot();
    }

}
