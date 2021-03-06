package com.example.fake9.tendee;

import android.os.SystemClock;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar mToolbar;
    private Spinner chooseDay;
    private TimePicker startTime;
    private TimePicker endTime;
    private Button busyBtn;
    private Button freeBtn;
    String day;
    ArrayList<Long> newDay;
    int startIndex = 0;
    int endIndex = 0;
    int intervals = 0;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

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
        busyBtn = (Button)findViewById(R.id.busy_btn);
        freeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour = startTime.getCurrentHour();
                int startMin = startTime.getCurrentMinute();
                int endHour = endTime.getCurrentHour();
                int endMin = endTime.getCurrentMinute();
                //Log.d("hour", "onClick: "+startHour);
                //Log.d("minute", "onClick: "+startMin);
                if (startHour <= 8 || startHour > 17 || endHour <= 8 || endHour > 17) {
                    Toast.makeText(ScheduleActivity.this, "invalid time", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (startHour > endHour) {
                    Toast.makeText(ScheduleActivity.this, "impossible time", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                /*if (startHour == endHour) {
                    if (startMin > endMin) {
                        Toast.makeText(ScheduleActivity.this, "Start time greater than end time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }*/
                startIndex = (startHour - 9) * 2;
                if (startMin >=30) {
                    startIndex = startIndex + 1;
                }
                //Log.d("startIndex", "onClick: "+startIndex);
                endIndex = (endHour - 9) * 2;
                /*if (endMin > 45) {
                    endIndex = endIndex + 1;
                }
                if (endMin <= 15) {
                    endIndex--;
                }*/

                Log.d("fuck", "onClick: "+startIndex);
                Log.d("click", "onClick: "+endIndex);
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                String current_uid = mCurrentUser.getUid();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("week").child(day);
                newDay = new ArrayList<Long>();
                mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newDay = ((ArrayList<Long>)dataSnapshot.getValue());
                        Log.d("newday", "onDataChange:"+newDay.toString());
                        for (int i = startIndex; i <= endIndex; i++) {
                            if (newDay.get(i) == 0) {
                                newDay.set(i,1L);
                            }
                        }
                        mUserDatabase.setValue(newDay);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(ScheduleActivity.this, "Free time has been set", Toast.LENGTH_SHORT).show();
            }
        });
        busyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour = startTime.getCurrentHour();
                int startMin = startTime.getCurrentMinute();
                int endHour = endTime.getCurrentHour();
                int endMin = endTime.getCurrentMinute();
                //Log.d("hour", "onClick: "+startHour);
                //Log.d("minute", "onClick: "+startMin);
                if (startHour < 9 || startHour > 17 || endHour < 9 || endHour > 17) {
                    Toast.makeText(ScheduleActivity.this, "invalid time", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (startHour > endHour) {
                    Toast.makeText(ScheduleActivity.this, "impossible time", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                startIndex = (startHour - 9) * 2;
                if (startMin >=30) {
                    startIndex = startIndex + 1;
                }
                //Log.d("startIndex", "onClick: "+startIndex);
                endIndex = (endHour - 9) * 2;
                if (endMin > 45) {
                    endIndex = endIndex + 1;
                }
                if (endMin <= 15) {
                    endIndex--;
                }
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                String current_uid = mCurrentUser.getUid();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("week").child(day);
                newDay = new ArrayList<Long>();
                mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newDay = ((ArrayList<Long>)dataSnapshot.getValue());
                        for (int i = startIndex; i <= endIndex; i++) {
                            if (newDay.get(i) != 0) {
                                newDay.set(i,0L);
                            }


                        }
                        mUserDatabase.setValue(newDay);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(ScheduleActivity.this, "Busy time has been set", Toast.LENGTH_SHORT).show();
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
