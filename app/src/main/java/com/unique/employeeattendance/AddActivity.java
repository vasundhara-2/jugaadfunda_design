package com.unique.employeeattendance;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.unique.employeeattendance.MainActivity.txt_breakend;
import static com.unique.employeeattendance.MainActivity.txt_breakstart;
import static com.unique.employeeattendance.MainActivity.txt_travelend;
import static com.unique.employeeattendance.MainActivity.txt_travelstart;
import static com.unique.employeeattendance.MainActivity.txt_workend;
import static com.unique.employeeattendance.MainActivity.txt_workstart;

public class AddActivity extends AppCompatActivity {

    private LinearLayout layoutStart, layoutFinish;
    private Calendar calendar;
    private String amPm;
    private TextView txt_finishtime, txt_starttime;
    private Button btn_Save;
    private String time1, time2;
    private long chosenTimeMills1, chosenTimeMills2;
    public static String time_difference;
    String value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getString("key");
        }
        if (value.equalsIgnoreCase("work")) {

            getSupportActionBar().setTitle("Add Work");
        }
        if (value.equalsIgnoreCase("break")) {

            getSupportActionBar().setTitle("Add Break");
        }
        if (value.equalsIgnoreCase("travel")) {

            getSupportActionBar().setTitle("Add Travel");
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendar = Calendar.getInstance();
        setContentView(R.layout.activity_add);
        btn_Save = findViewById(R.id.btnSave);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    long difference = chosenTimeMills1 - chosenTimeMills2;
                    long differenceInMilliSeconds
                            = Math.abs(difference);

                    // Calculating the difference in Hours
                    long differenceInHours
                            = (differenceInMilliSeconds / (60 * 60 * 1000))
                            % 24;

                    // Calculating the difference in Minutes
                    long differenceInMinutes
                            = (differenceInMilliSeconds / (60 * 1000)) % 60;

                    // Calculating the difference in Seconds
                    long differenceInSeconds
                            = (differenceInMilliSeconds / 1000) % 60;

                    time_difference = differenceInHours + " Hrs " + differenceInMinutes + " m";
                    if (value.equalsIgnoreCase("work")) {
                        MainActivity.txt_workdifference.setText(time_difference);
                        txt_workstart.setText(time1);
                        txt_workend.setText(time2);
                    }
                    if (value.equalsIgnoreCase("break")) {

                        MainActivity.txt_breakdifference.setText(time_difference);
                        txt_breakstart.setText(time1);
                        txt_breakend.setText(time2);
                    }

                    if (value.equalsIgnoreCase("travel")) {

                        MainActivity.txt_traveldifference.setText(time_difference);
                        txt_travelstart.setText(time1);
                        txt_travelend.setText(time2);
                    }
                    Log.d("TAGG", "Difference is: " + differenceInHours + " Hours and " + differenceInMinutes + " minutes");

                    finish();

                }
                           }
        });
        txt_starttime = findViewById(R.id.txt_starttime);
        txt_finishtime = findViewById(R.id.txt_finishtime);
        layoutStart = findViewById(R.id.layout_startwork);
        layoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                chosenTimeMills1 = calendar.getTimeInMillis();
                                time1 = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                                Log.d("MainActivity", "Selected time is " + time1);


                                if(calendar.after(GregorianCalendar.getInstance())){
                                    Toast.makeText(AddActivity.this, "Cannot select a future time", Toast.LENGTH_SHORT).show();
                                } else {
                                    Calendar datetime=Calendar.getInstance();
                                    datetime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                    datetime.set(Calendar.MINUTE,minute);
                                    SimpleDateFormat mSDF=new SimpleDateFormat(" hh: mm a");
                                    txt_starttime.setText(mSDF.format(datetime.getTime()));  // make sure this is accessible
                                }
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }

                               // txt_starttime.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();
            }
        });
        layoutFinish = findViewById(R.id.layout_finishwork);
        layoutFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                if(calendar.after(GregorianCalendar.getInstance())){
                                    Toast.makeText(AddActivity.this, "Cannot select a future time", Toast.LENGTH_SHORT).show();
                                }
                                chosenTimeMills2 = calendar.getTimeInMillis();
                                time2 = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                                Log.d("MainActivity", "Selected time is " + time2);
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }

                                txt_finishtime.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);

                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();
            }
        });

    }

    private boolean validation() {
        boolean ret = true;
        if(TextUtils.isEmpty(txt_starttime.getText().toString())&&TextUtils.isEmpty(txt_finishtime.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Time fields cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if (chosenTimeMills1 > chosenTimeMills2) {
                Toast.makeText(getApplicationContext(), "Start Time Should Be Less Than End Time", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}