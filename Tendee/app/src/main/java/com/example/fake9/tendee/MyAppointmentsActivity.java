package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyAppointmentsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mScheduler_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        mToolbar = (Toolbar)findViewById(R.id.my_appointments_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Appointments");

        mScheduler_btn = (Button)findViewById(R.id.schedulerbtn);
        mScheduler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleIntent = new Intent(MyAppointmentsActivity.this,ScheduleActivity.class);
                startActivity(scheduleIntent);
            }
        });

    }
}
