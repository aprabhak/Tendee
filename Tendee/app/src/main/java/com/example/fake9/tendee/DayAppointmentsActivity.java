package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class DayAppointmentsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    String day;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mApptDatabase;
    private FirebaseUser mCurrentUser;
    ArrayList<Long> daySchedule;
    ArrayList<Integer> startIndexes;
    ArrayList<Integer> endIndexes;
    ArrayList<String> timeIntervals;
    private ListView listView;
    private ListAdapter listAdapter;
    ArrayList<Integer> apptindex;
    ArrayList<Long> apptnumber;
    HashMap<String,Long> match;
    private Button testbtn;



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
                Date now = Calendar.getInstance().getTime();
                int nowday = now.getDay();
                int nowhour = now.getHours();
                int nowmin = now.getMinutes();
                int nowindex = 0;
                String nowdayString = "";
                if (nowday == 0) {
                    nowdayString = "Sunday";
                }
                if (nowday == 1) {
                    nowdayString = "Monday";
                }
                if (nowday == 2) {
                    nowdayString = "Tuesday";
                }
                if (nowday == 3) {
                    nowdayString = "Wednesday";
                }
                if (nowday == 4) {
                    nowdayString = "Thursday";
                }
                if (nowday == 5) {
                    nowdayString = "Friday";
                }
                if (nowday == 6) {
                    nowdayString = "Saturday";
                }
                Log.d("now", "onDataChange: "+nowhour);
                //nowdayString = "Monday";
                if (nowdayString.equals(day)) {
                    nowindex = (nowhour - 9) * 2;
                    if (nowmin >=30) {
                        nowindex = nowindex + 1;
                    }
                    if (nowhour  < 9 || nowhour > 17) {
                        nowindex = 0;
                    }
                    //nowindex = 2;
                    for (int x = 0; x < nowindex; x++) {
                        daySchedule.set(x,0L);
                    }
                    mUserDatabase.setValue(daySchedule);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daySchedule = (ArrayList<Long>)dataSnapshot.getValue();
                Log.d("daySchedule", "onDataChange: "+daySchedule.toString());
                int k = 0;
                startIndexes = new ArrayList<Integer>();
                endIndexes = new ArrayList<Integer>();
                timeIntervals = new ArrayList<String>();
                apptindex = new ArrayList<Integer>();
                apptnumber = new ArrayList<Long>();
                for (int i = 0; i < daySchedule.size(); i++) {
                    //starthour = daySchedule.get(i);
                    if (daySchedule.get(i) == 1L) {
                        //Log.d("checkone", "onDataChange: ");
                        startIndexes.add(i);
                        k = i;
                        while (k <= 15 && daySchedule.get(k) == 1L) {
                            k = k + 1;
                        }
                        endIndexes.add(k);
                    } else if (daySchedule.get(i) > 1) {
                        //Toast.makeText(DayAppointmentsActivity.this, "hi", Toast.LENGTH_SHORT).show();
                        Log.d("LMAO","LMAO"+i+"--"+daySchedule.get(i));
                        apptindex.add(i);
                        apptnumber.add(daySchedule.get(i));
                    }
                }

                Log.d("appointmentnumbers", "onDataChange: "+apptnumber.toString());
                Log.d("startindexes", "onDataChange: "+startIndexes.toString());
                Log.d("endindexes", "onDataChange: "+endIndexes.toString());
                int starthour = 0;
                int startmin = 0;
                int endhour = 0;
                int endmin = 0;
                for (int j = 0; j < startIndexes.size(); j++) {
                    starthour = startIndexes.get(j);
                    int check1 = starthour;
                    if (starthour == 0) {
                        starthour = starthour + 9;
                    } else if (starthour % 2 == 0) {
                        starthour = 9 + (starthour / 2);
                    } else {
                        starthour = 9 + (starthour / 2);
                        startmin = 30;
                    }
                    endhour = endIndexes.get(j);
                    int check2 = endhour;
                    if (endhour == 0) {
                        endhour = endhour + 9;
                    } else if (endhour % 2 == 0) {
                        endhour = 9 + (endhour / 2);
                    } else {
                        endhour = 9 + (endhour / 2);
                        endmin = 30;
                    }
                    if (check1 == check2) {
                        if (check1 == 0) {
                            endmin = 30;
                        } else if (check2 % 2 == 0) {
                            endmin = 30;
                        } else {
                            endmin = 0;
                            endhour = endhour + 1;
                        }
                    }
                    Log.d("startingtime", "onDataChange: "+starthour + " " +startmin);
                    Log.d("endingtime", "onDataChange: "+endhour + " " +endmin);
                    String time = "Free: " + starthour + ":" + startmin + " to " + endhour + ":" + endmin;
                    timeIntervals.add(time);
                    //Toast.makeText(DayAppointmentsActivity.this, time, Toast.LENGTH_SHORT).show();
                    startmin = 0;
                    endmin = 0;
                }
                //final Semaphore semaphore = new Semaphore(0);
                for (int j = 0; j < apptindex.size(); j++) {
                    int index = apptindex.get(j);
                    long apptnum = apptnumber.get(j);
                    String time = "";
                    if (index == 0) {
                        time = "9:00 to 9:30";
                    } else if (index % 2 == 0) {
                        index = 9 + (index / 2);
                        time = index+ ":00" + " to "+index+":30";
                    } else if (index % 2 != 0) {
                        index = 9 + (index / 2);
                        int temp = index + 1;
                        time = index+":30" + " to "+temp;
                    }
                    String apptString = "appointment-"+apptnum+":"+time;
                    timeIntervals.add(apptString);
                }
                Log.d("timeintervals", "onDataChange: "+timeIntervals.toString());
                listAdapter = new ArrayAdapter<String>(DayAppointmentsActivity.this,android.R.layout.simple_list_item_1,timeIntervals);
                listView.setAdapter(listAdapter);
                //listAdapter.notify();
                //listView.refreshDrawableState();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String result = String.valueOf(adapterView.getItemAtPosition(i));
                        if (result.contains("Free")) {
                            Toast.makeText(DayAppointmentsActivity.this, "No further details", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            int start = result.indexOf('-');
                            int end = result.indexOf(':');
                            Log.d("oi", "onItemClick: "+start);
                            Log.d("oi", "onItemClick: "+end);
                            String newstring = result.substring((start+1),end);
                            //Toast.makeText(DayAppointmentsActivity.this, newstring, Toast.LENGTH_SHORT).show();
                            Intent apptdetails = new Intent(DayAppointmentsActivity.this,AppointmentDetailsActivity.class);
                            apptdetails.putExtra("apptnum",newstring);
                            startActivity(apptdetails);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
