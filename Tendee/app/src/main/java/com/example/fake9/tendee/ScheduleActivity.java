package com.example.fake9.tendee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar mToolbar;
    private Spinner chooseDay;
    private TimePicker startTime;
    private TimePicker endTime;
    private Button busyBtn;
    private Button freeBtn;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mToolbar = (Toolbar)findViewById(R.id.schedule_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Schedule");

        chooseDay = (Spinner)findViewById(R.id.chooseDay_spinner);
        chooseDay.setOnItemSelectedListener(this);
        List<String> week = new ArrayList<String>();
        week.add("Monday");
        week.add("Tuesday");
        week.add("Wednesday");
        week.add("Thursday");
        week.add("Friday");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,week);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseDay.setAdapter(dataAdapter);

        startTime = (TimePicker)findViewById(R.id.start_timePicker);
        endTime = (TimePicker)findViewById(R.id.end_timePicker);
        freeBtn = (Button)findViewById(R.id.free_btn);
        freeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour = startTime.getCurrentHour();
                int startMin = startTime.getCurrentMinute();
                int endHour = endTime.getCurrentHour();
                int endMin = endTime.getCurrentMinute();
                int startIndex = 0;
                int endIndex = 0;
                Log.d("hour", "onClick: "+startHour);
                Log.d("minute", "onClick: "+startMin);
                if (startHour < 9 || startHour > 17) {
                    Toast.makeText(ScheduleActivity.this, "invalid time", Toast.LENGTH_SHORT).show();
                    return;
                }
                startIndex = (startHour - 9) * 2;
                if (startMin >= 30) {
                    startIndex = startIndex + 1;
                }
                Log.d("startIndex", "onClick: "+startIndex);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        day = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
