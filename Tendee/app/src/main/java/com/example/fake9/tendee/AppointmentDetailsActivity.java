package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentDetailsActivity extends AppCompatActivity {

    String apptnum;
    private TextView apptdate;
    private TextView addressdisplay;
    private TextView phonenum;
    private TextView reason;
    private TextView selfattendee;
    private TextView targetname;
    private TextView timeview;
    private DatabaseReference apptdatabase;
    private DatabaseReference userdatabase;
    private Button deletebtn;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        /*mToolbar = (Toolbar)findViewById(R.id.appt_details_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Appointment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Intent myIntent = getIntent();
        apptnum = myIntent.getStringExtra("apptnum");
        Log.d("apptnum", "onCreate: "+apptnum);

        apptdate = (TextView)findViewById(R.id.apptdate);

        phonenum = (TextView)findViewById(R.id.phonenum);
        reason = (TextView)findViewById(R.id.reason);
        selfattendee = (TextView)findViewById(R.id.selfattendee);
        targetname = (TextView)findViewById(R.id.targetname);
        timeview = (TextView)findViewById(R.id.timeview);
        addressdisplay = (TextView)findViewById(R.id.address);
        deletebtn = (Button)findViewById(R.id.deleteappt);

        reason.setMovementMethod(new ScrollingMovementMethod());
        addressdisplay.setMovementMethod(new ScrollingMovementMethod());

        apptdatabase = FirebaseDatabase.getInstance().getReference().child("Appointments").child(apptnum);
//        Toast.makeText(AppointmentDetailsActivity.this, "apptnum is "+apptnum, Toast.LENGTH_SHORT).show();

        apptdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("date").getValue()!=null){
                final String Date = dataSnapshot.child("date").getValue().toString();
                final String OtherAttendee = dataSnapshot.child("otherAttendee").getValue().toString();
                String Phonenum = dataSnapshot.child("phone").getValue().toString();
                String Reason = dataSnapshot.child("reason").getValue().toString();
                final String SelfAttendee = dataSnapshot.child("selfAttendee").getValue().toString();
                final String TargetName = dataSnapshot.child("targetName").getValue().toString();
                final String Time = dataSnapshot.child("time").getValue().toString();
                final String Address = dataSnapshot.child("address").getValue().toString();
                String attendee_list;
                apptdate.setText(Date);
                addressdisplay.setText(Address);
                phonenum.setText(Phonenum);
                reason.setText(Reason);
                if (OtherAttendee.equals(" ")) {
                    selfattendee.setText(SelfAttendee);
                } else {
                    attendee_list = SelfAttendee + ", " + OtherAttendee;
                    selfattendee.setText(attendee_list);
                }
                targetname.setText(TargetName);
                timeview.setText(Time);

                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userdatabase = FirebaseDatabase.getInstance().getReference("Users");
                        Query query = userdatabase.orderByChild("name").equalTo(OtherAttendee);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    childSnapshot = childSnapshot.child("week");
                                    childSnapshot = childSnapshot.child(Date);

                                    for (int i = 0; i <= 16; i++) {
                                        int status = Integer.parseInt(childSnapshot.child(Integer.toString(i)).getValue().toString());
                                        //   String address = dataSnapshot.getRef().ge;

                                        if (status == Integer.parseInt(apptnum)) {
                                            childSnapshot.child(Integer.toString(i)).getRef().setValue(0);
                                        }
                                    }
                                }


//                        Toast.makeText(Make_AppointmentActivity.this, time.toString(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(AppointmentDetailsActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Query queryforselfTargetUser = userdatabase.orderByChild("name").equalTo(TargetName);
                        queryforselfTargetUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    childSnapshot = childSnapshot.child("week");
                                    childSnapshot = childSnapshot.child(Date);

                                    for (int i = 0; i <= 16; i++) {
                                        int status = Integer.parseInt(childSnapshot.child(Integer.toString(i)).getValue().toString());
                                        //   String address = dataSnapshot.getRef().ge;

                                        if (status == Integer.parseInt(apptnum)) {
                                            childSnapshot.child(Integer.toString(i)).getRef().setValue(0);
                                        }
                                    }
                                }


//                        Toast.makeText(Make_AppointmentActivity.this, time.toString(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(AppointmentDetailsActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Query queryforself = userdatabase.orderByChild("name").equalTo(SelfAttendee);
                        queryforself.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    childSnapshot = childSnapshot.child("week");
                                    childSnapshot = childSnapshot.child(Date);

                                    for (int i = 0; i <= 16; i++) {
                                        int status = Integer.parseInt(childSnapshot.child(Integer.toString(i)).getValue().toString());
                                        //   String address = dataSnapshot.getRef().ge;

                                        if (status == Integer.parseInt(apptnum)) {
                                            childSnapshot.child(Integer.toString(i)).getRef().setValue(0);
                                        }
                                    }
                                }


//                        Toast.makeText(Make_AppointmentActivity.this, time.toString(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(AppointmentDetailsActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent mainIntent = new Intent(AppointmentDetailsActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                });

            }else{
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
