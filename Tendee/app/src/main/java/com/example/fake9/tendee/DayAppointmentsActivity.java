package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class DayAppointmentsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_appointments);

        Intent myIntent = getIntent();
        day = myIntent.getStringExtra("day");
        //Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
        mToolbar = (Toolbar)findViewById(R.id.day_appt_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
