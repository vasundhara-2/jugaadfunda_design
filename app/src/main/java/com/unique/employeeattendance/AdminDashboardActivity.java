package com.unique.employeeattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView card_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        card_summary = findViewById(R.id.card_summary);
        card_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminDashboardActivity.this, SummaryReportActivity.class));
            }
        });
    }
}