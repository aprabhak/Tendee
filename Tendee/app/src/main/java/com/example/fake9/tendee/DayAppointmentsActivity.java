package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class DayAppointmentsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    String day;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    ArrayList<Long> daySchedule;
    ArrayList<Integer> startIndexes;
    ArrayList<Integer> endIndexes;
    ArrayList<String> timeIntervals;
    private ListView listView;
    private ListAdapter listAdapter;

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
        listView = (ListView)findViewById(R.id.dayIntervals);



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id).child("week").child(day);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daySchedule = (ArrayList<Long>)dataSnapshot.getValue();
                Log.d("daySchedule", "onDataChange: "+daySchedule.toString());
                int k = 0;
                startIndexes = new ArrayList<Integer>();
                endIndexes = new ArrayList<Integer>();
                timeIntervals = new ArrayList<String>();
                for (int i = 0; i < daySchedule.size(); i++) {
                    //starthour = daySchedule.get(i);
                    if (daySchedule.get(i) == 1L) {
                        //Log.d("checkone", "onDataChange: ");
                        startIndexes.add(i);
                        k = i;
                        while (daySchedule.get(k) == 1L) {
                            k = k + 1;
                        }
                        endIndexes.add(k-1);
                        i = k;
                    }
                }
                Log.d("startindexes", "onDataChange: "+startIndexes.toString());
                Log.d("endindexes", "onDataChange: "+endIndexes.toString());
                int starthour = 0;
                int startmin = 0;
                int endhour = 0;
                int endmin = 0;
                for (int j = 0; j < startIndexes.size(); j++) {
                    starthour = startIndexes.get(j);
                    if (starthour == 0) {
                        starthour = starthour + 9;
                    } else if (starthour % 2 == 0) {
                        starthour = 9 + (starthour / 2);
                    } else {
                        starthour = 9 + (starthour / 2);
                        startmin = 30;
                    }
                    endhour = endIndexes.get(j);
                    if (endhour == 0) {
                        endhour = endhour + 9;
                    } else if (endhour % 2 == 0) {
                        endhour = 9 + (endhour / 2);
                    } else {
                        endhour = 9 + (endhour / 2);
                        endmin = 30;
                    }
                    Log.d("startingtime", "onDataChange: "+starthour + " " +startmin);
                    Log.d("endingtime", "onDataChange: "+endhour + " " +endmin);
                    String time = "Free: " + starthour + ":" + startmin + " to " + endhour + ":" + endmin;
                    timeIntervals.add(time);
                    //Toast.makeText(DayAppointmentsActivity.this, time, Toast.LENGTH_SHORT).show();
                    startmin = 0;
                    endmin = 0;
                }
                Log.d("timeintervals", "onDataChange: "+timeIntervals.toString());
                listAdapter = new ArrayAdapter<String>(DayAppointmentsActivity.this,android.R.layout.simple_list_item_1,timeIntervals);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
