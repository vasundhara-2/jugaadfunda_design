package com.unique.employeeattendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.unique.employeeattendance.common.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class SummaryReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private LinearLayout fromdate, tilldate,layout_data;
    private static TextView txt_startdate, txt_enddate;
    private String tag;
private Button btn_show,btn_clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);
        txt_startdate = findViewById(R.id.txt_startdate);
        txt_enddate = findViewById(R.id.txt_enddate);
        layout_data = findViewById(R.id.layout_data);
        fromdate = findViewById(R.id.layout_fromdate);
        tilldate = findViewById(R.id.layout_tilldate);
        btn_show = findViewById(R.id.btnShow);
        btn_clear = findViewById(R.id.btn_clear);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_data.setVisibility(View.VISIBLE);
            }
        });

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = "datepicker1";
                DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        tilldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = "datepicker2";
                DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK2");
            }
        });

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        if (tag.equalsIgnoreCase("datepicker1")) {
            txt_startdate.setText(selectedDate);
        } else {
            txt_enddate.setText(selectedDate);
        }

    }


}
