package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAppointmentsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mScheduler_btn;
    private ListView listView;

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

        listView = (ListView)findViewById(R.id.daylist);
        String[] values = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String result = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(MyAppointmentsActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }
}
