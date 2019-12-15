package com.mohamedelloumi.tripapp.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CustomDataBinding {
    @BindingAdapter("suggestions")
    public static void setSuggestions(final AutoCompleteTextView autoCompleteTextView, ObservableArrayList<String> citiesList) {
        if (autoCompleteTextView.getAdapter() == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), android.R.layout.simple_list_item_1, citiesList);
            autoCompleteTextView.setAdapter(adapter);
        }
    }

    @BindingAdapter("onClickAutoComplete")
    public static void setOnClickAutoComplete(final AutoCompleteTextView autoCompleteTextView, final ObservableField<String> address) {
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                address.set(autoCompleteTextView.getText().toString());
            }
        });
    }

    @BindingAdapter({"departureDate", "arrivalDate"})
    public static void setonClickDatePicker(final EditText editText, final ObservableField<String> departureDate, final ObservableField<String> arrivalDate) {
        final Calendar calendar = Calendar.getInstance();
        if ((departureDate.get() == null) || (departureDate.get().equals(""))) {
            updateDatePickerInput(calendar, departureDate, arrivalDate);
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(editText.getContext(), selectDate(calendar, departureDate, arrivalDate), calendar
                        .get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private static DatePickerDialog.OnDateSetListener selectDate(final Calendar myCalendar, final ObservableField<String> departureDate, final ObservableField<String> arrivalDate) {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDatePickerInput(myCalendar, departureDate, arrivalDate);
            }

        };
        return date;
    }

    private static void updateDatePickerInput(Calendar calendar, ObservableField<String> departureDate, ObservableField<String> arrivalDate) {
        String requiredFormat = "dd.MM.yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat, Locale.GERMANY);
        departureDate.set(simpleDateFormat.format(calendar.getTime()));
        arrivalDate.set(simpleDateFormat.format(calendar.getTime()));
    }

    @BindingAdapter({"departureTime", "arrivalTime"})
    public static void setonClickTimePicker(final EditText editText, final ObservableField<String> departureTime, final ObservableField<String> arrivalTime) {
        final Calendar calendar = Calendar.getInstance();
        if ((departureTime.get() == null) || (departureTime.get().equals(""))) {
            updateTimePickerInput(calendar, departureTime, arrivalTime);
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(editText.getContext(), selectTime(calendar, departureTime, arrivalTime), calendar
                        .get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private static TimePickerDialog.OnTimeSetListener selectTime(final Calendar myCalendar, final ObservableField<String> departureTime, final ObservableField<String> arrivalTime) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimePickerInput(myCalendar, departureTime, arrivalTime);
            }
        };
        return timeSetListener;
    }

    private static void updateTimePickerInput(Calendar calendar, ObservableField<String> departureTime, ObservableField<String> arrivalTime) {
        final Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        String requiredFormat = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat, Locale.GERMANY);
        departureTime.set(simpleDateFormat.format(calendar.getTime()));
        arrivalTime.set(simpleDateFormat.format(currentDate.getTime()));
    }

}
