package com.unique.employeeattendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LeavesActivity extends AppCompatActivity {

    private TextView txt_finishdate, txt_startdate;
    private LinearLayout layout_fromdate, layout_todate;
    private Calendar calendar;
    private EditText etLeaveReason;
    private Button btnSubmit;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);
        getSupportActionBar().setTitle("Apply For Leave");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendar = Calendar.getInstance();

        etLeaveReason = findViewById(R.id.etLeaveReason);
        btnSubmit = findViewById(R.id.btnSubmit);
        txt_startdate = findViewById(R.id.txt_startdate);
        txt_finishdate = findViewById(R.id.txt_finishdate);
        layout_fromdate = findViewById(R.id.layout_fromdate);
        layout_todate = findViewById(R.id.layout_todate);
        loadingPB = findViewById(R.id.idLoadingPB);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    applyForLeave();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            layout_fromdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(LeavesActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                            Log.d("MainActivity", "Selected date is " + date);

                            txt_startdate.setText(date);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });


            layout_todate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(LeavesActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                            Log.d("MainActivity", "Selected date is " + date);

                            txt_finishdate.setText(date);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void applyForLeave() throws JSONException {

        String url = "http://3.108.182.64/auth/api/leave/create";
        loadingPB.setVisibility(View.VISIBLE);

            JSONObject postparams = new JSONObject();
            postparams.put("employee", "2");
            postparams.put("fromdate", "2022-10-1");
            postparams.put("todate", "2022-20-1");
            postparams.put("countDay", "10");

        RequestQueue queue = Volley.newRequestQueue(LeavesActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postparams, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingPB.setVisibility(View.GONE);
                etLeaveReason.setText("");

                Toast.makeText(LeavesActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(String.valueOf(response));
                    Log.d("Response:", respObj.toString());
                    //String name = respObj.getString("name");
                    //String job = respObj.getString("job");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(LeavesActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        queue.add(request);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                queue.getCache().clear();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}