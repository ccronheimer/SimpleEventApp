package com.cameroncronheimer.eventapp;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {



    private DatePickerDialog datePickerDialog;

    private Button dateButton;
    private Button timeButton;
    int hour, minute;

    String date;
    String time;

    int preDate;
    int preTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        EditText titleInput = findViewById(R.id.titleinput);
        EditText locationInput = findViewById(R.id.locationinput);

        MaterialButton saveBtn = findViewById(R.id.savebtn);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        date = getTodaysDate();

        timeButton = findViewById(R.id.timePickerButton);
        timeButton.setText(getTodaysTime());
        time = getTodaysTime();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String location = locationInput.getText().toString();

                realm.beginTransaction();
                Event event = realm.createObject(Event.class);

                event.setTitle(title); // set event title
                event.setDate(date); // set date
                event.setTime(time); // set time
                event.setLocation(location);

                event.setDateTime(preDate+preTime); // for sorting later
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(),"Event saved",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }



    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private String getTodaysTime() {
        return DateFormat.getTimeInstance().format(System.currentTimeMillis()); // default time
    }

    // date picker popup
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                 date = makeDateString(day, month, year);
                dateButton.setText(date);
                preDate = year+month+day;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1) {
            return "JAN";
        }
        if(month == 2) {
            return "FEB";
        }
        if(month == 3) {
            return "MAR";
        }
        if(month == 4) {
            return "APR";
        }
        if(month == 5) {
            return "MAY";
        }
        if(month == 6) {
            return "JUN";
        }
        if(month == 7) {
            return "JUL";
        }
        if(month == 8) {
            return "AUG";
        }
        if(month == 9) {
            return "SEP";
        }
        if(month == 10) {
            return "OCT";
        }
        if(month == 11) {
            return "NOV";
        }
        if(month == 12) {
            return "DEC";
        }
        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    // time picker popup
    public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                hour = hourOfDay;
                minute = min;
                timeButton.setText(String.format(Locale.CANADA, "%02d:%02d", hour, minute));
                time = String.format(Locale.CANADA, "%02d:%02d", hour, minute);
                preTime = hourOfDay + min;
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Select Time");

        timePickerDialog.show();
    }
}