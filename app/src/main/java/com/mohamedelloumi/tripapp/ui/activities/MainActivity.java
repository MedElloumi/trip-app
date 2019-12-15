package com.mohamedelloumi.tripapp.ui.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mohamedelloumi.tripapp.R;
import com.mohamedelloumi.tripapp.ui.fragments.FormFragment;
import com.mohamedelloumi.tripapp.utils.ApplicationContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationContext.getInstance().init(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FormFragment formFragment = new FormFragment();
        fragmentTransaction.add(R.id.container, formFragment);
        fragmentTransaction.commit();
    }
}
