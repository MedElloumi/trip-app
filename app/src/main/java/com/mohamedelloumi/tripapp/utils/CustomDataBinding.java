package com.mohamedelloumi.tripapp.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class CustomDataBinding {
    @BindingAdapter("suggestions")
    public static void setSuggestions(AutoCompleteTextView autoCompleteTextView, ObservableArrayList<String> citiesList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(autoCompleteTextView.getContext(), android.R.layout.simple_list_item_1, citiesList);
        autoCompleteTextView.setAdapter(adapter);
    }
}
