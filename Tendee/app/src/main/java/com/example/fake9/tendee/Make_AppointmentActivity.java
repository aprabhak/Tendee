package com.example.fake9.tendee;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Make_AppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private String selectedDate;
    private Boolean dateSelected = false;
    private Spinner time_spinner;
    private TextView display_attendee;
    private List<String> time;
    private List<String> week;
    private String target_user_name;
    private Button mAddAttendee;
    private Button mLastStep;
    public String attendee_list;
    public String date;
    public String app_time;

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//        String saved_date = savedInstanceState.getString("Date");
//        String saved_time = savedInstanceState.getString("Time");
//        String saved_attendees = savedInstanceState.getString("Attendees");
//        String myString = savedInstanceState.getString("MyString");
//        attendee_list=saved_attendees;
//        date=saved_date;
//        app_time=saved_time;
//        display_attendee.setText(date);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_app);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        // Spinner element
        Spinner date_spinner = (Spinner) findViewById(R.id.week_spinner);
        time_spinner = (Spinner) findViewById(R.id.time_spinner);
        mAddAttendee = findViewById(R.id.add_attendee_btn);
        mLastStep = findViewById(R.id.ToLastStepBtn);

        // Spinner click listener
        date_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        time_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Drop down elements
        week = new ArrayList<String>();
        week.add("Monday");
        week.add("Tuesday");
        week.add("Wednesday");
        week.add("Thursday");
        week.add("Friday");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, week);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        date_spinner.setAdapter(dataAdapter);


        mLastStep.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(app_time!=null){
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String current_uid = mCurrentUser.getUid();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
                mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                        //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                        String index = parseTime(app_time);
                        if (Integer.parseInt(dataSnapshot.child("week").child(date).child(index).getValue().toString()) != 0 &&
                                Integer.parseInt(dataSnapshot.child("week").child(date).child(index).getValue().toString()) != 1) {

                            Toast.makeText(Make_AppointmentActivity.this, "You are not free at this time!!", Toast.LENGTH_SHORT).show();

//                            SystemClock.sleep(2000);

                            Intent intent = new Intent(Make_AppointmentActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                Intent intent = new Intent(Make_AppointmentActivity.this, MakeAppLastStepActivity.class);

                intent.putExtra("ATTENDEE_NAME", " ");

                intent.putExtra("APPOINTMENT_DATE", date);

                intent.putExtra("APPOINTMENT_TIME", app_time);

                intent.putExtra("APPOINTMENT_TARGET", target_user_name);

//                Toast.makeText(Make_AppointmentActivity.this, "time is" + app_time + "date is ~" + date, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                }else{
                    Toast.makeText(Make_AppointmentActivity.this, "Please Choose Time!!", Toast.LENGTH_SHORT).show();
                }

            }

            //
        });


        mAddAttendee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String name = mSearchName.getEditText().getText().toString();

                if(app_time!=null){
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String current_uid = mCurrentUser.getUid();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
                mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                        //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                        String index = parseTime(app_time);
                        if (Integer.parseInt(dataSnapshot.child("week").child(date).child(index).getValue().toString()) != 0 &&
                                Integer.parseInt(dataSnapshot.child("week").child(date).child(index).getValue().toString()) != 1) {

                            Toast.makeText(Make_AppointmentActivity.this, "You are not free at this time!!", Toast.LENGTH_SHORT).show();

//                            SystemClock.sleep(2000);

                            Intent intent = new Intent(Make_AppointmentActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                Intent intent = new Intent(Make_AppointmentActivity.this, Add_attendeeActivity.class);

                intent.putExtra("App_Date", date);

                intent.putExtra("app_Time", app_time);

                intent.putExtra("TARGET_NAME", target_user_name);

//                Toast.makeText(Make_AppointmentActivity.this, "time is" + app_time + "date is ~" + date, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                }else{
                    Toast.makeText(Make_AppointmentActivity.this, "Please Choose Time!!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        // On selecting a spinner item
        final String item = adapterView.getItemAtPosition(position).toString();
        selectedDate = item;

        dateSelected = true;

        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.week_spinner) {
            if (week.contains(item)) {
                date = item;
                target_user_name = getIntent().getStringExtra("target_user_name");
                Query query = mUserDatabase.orderByChild("name").equalTo(target_user_name);//ok

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        time = new ArrayList<>();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                            childSnapshot = childSnapshot.child("week");
                            childSnapshot = childSnapshot.child(item);
                            for (int i = 0; i <= 16; i++) {
                                int status = Integer.parseInt(childSnapshot.child(Integer.toString(i)).getValue().toString());
                                //   String address = dataSnapshot.getRef().ge;

                                if (status == 1) {
                                    if (i % 2 == 0) {
                                        time.add(9 + i / 2 + ":00");
                                    } else {
                                        time.add(9 + (i - 1) / 2 + "" + ":30");
                                    }
                                }


                            }
                        }

                        ArrayAdapter<String> time_Adapter = new ArrayAdapter<>
                                (Make_AppointmentActivity.this, android.R.layout.simple_spinner_item, time);

                        // Drop down layout style - list view with radio button
                        time_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        if (time != null) {
                            // attaching data adapter to spinner
                            time_spinner.setAdapter(time_Adapter);
                        }


//                        Toast.makeText(Make_AppointmentActivity.this, time.toString(), Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Make_AppointmentActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else if (spinner.getId() == R.id.time_spinner) {
            app_time = item;
//            Toast.makeText(Make_AppointmentActivity.this, "item is" + item, Toast.LENGTH_SHORT).show();
        }


        //  Toast.makeText(view.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String parseTime(String time) {
        String res[];
        res = time.split(":");
        int index = 0;
        if (Integer.parseInt(res[1]) == 30) {
            index++;
        }
        index += (Integer.parseInt(res[0]) - 9) * 2;
        return index + "";
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putString("Date", date);
//        savedInstanceState.putString("Time", app_time);
//        savedInstanceState.putString("Attendees", attendee_list);
//        savedInstanceState.putString("MyString", "Welcome back to Android");
//        // etc.
//    }

}

