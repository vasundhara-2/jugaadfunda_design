package com.unique.employeeattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private CalendarView calendarView;
    private String month;
    private TextView selectedDate;
    private TextView btnAddwork, btnAddbreak, btnAddtravel;
    public static TextView txt_workdifference, txt_breakdifference, txt_traveldifference, txt_workstart, txt_travelstart,
            txt_breakstart, txt_workend, txt_breakend, txt_travelend;
    private LinearLayout layout_work, layout_break, layout_travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Add Work Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();
        Calendar twoDaysAgo = (Calendar) calendar.clone();
        twoDaysAgo.add(Calendar.DATE, -7);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate(twoDaysAgo.getTimeInMillis());
        selectedDate = findViewById(R.id.txt_selectedDate);
        txt_workdifference = findViewById(R.id.txt_workdiff);
        txt_breakdifference = findViewById(R.id.txt_breakdiff);
        txt_traveldifference = findViewById(R.id.txt_traveldiff);
        txt_workstart = findViewById(R.id.txt_workstart);
        txt_travelstart = findViewById(R.id.txt_travelstart);
        txt_breakstart = findViewById(R.id.txt_breakstart);
        txt_workend = findViewById(R.id.txt_workend);
        txt_breakend = findViewById(R.id.txt_breakend);
        txt_travelend = findViewById(R.id.txt_travelend);

        layout_work = findViewById(R.id.layout_work);
        layout_break = findViewById(R.id.layout_break);
        layout_travel = findViewById(R.id.layout_travel);

        calendarView.setMaxDate(System.currentTimeMillis());
        btnAddwork = findViewById(R.id.btn_addwork);
        btnAddbreak = findViewById(R.id.btn_addbreak);
        btnAddtravel = findViewById(R.id.btn_addtravel);
        btnAddwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(MainActivity.this, AddActivity.class));
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("key", "work");
                startActivity(i);
                layout_work.setVisibility(View.VISIBLE);
            }
        });

        btnAddbreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("key", "break");
                startActivity(i);
                layout_break.setVisibility(View.VISIBLE);
            }
        });

        btnAddtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("key", "travel");
                startActivity(i);
                layout_travel.setVisibility(View.VISIBLE);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Log.d("Calender", "i" + i + "i1" + i1 + "i2" + i2);

                int monCount = (i1 + 1);
                switch (monCount) {
                    case 1:
                        month = " January ";
                        break;
                    case 2:
                        month = " February ";
                        break;
                    case 3:
                        month = " March ";
                        break;
                    case 4:
                        month = " April ";
                        break;
                    case 5:
                        month = " May ";
                        break;
                    case 6:
                        month = " June ";
                        break;
                    case 7:
                        month = " July ";
                        break;
                    case 8:
                        month = " August ";
                        break;
                    case 9:
                        month = " September ";
                        break;
                    case 10:
                        month = " October ";
                        break;
                    case 11:
                        month = " November ";
                        break;
                    case 12:
                        month = " December ";
                        break;
                }

                String msg = i2 + month + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                Date currentTime = Calendar.getInstance().getTime();
                Log.d("currentTime:", String.valueOf(currentTime));
                selectedDate.setText(msg);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}