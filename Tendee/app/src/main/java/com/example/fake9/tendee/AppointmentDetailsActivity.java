package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentDetailsActivity extends AppCompatActivity {

    String apptnum;
    private TextView apptdate;
    private TextView otherattendee;
    private TextView phonenum;
    private TextView reason;
    private TextView selfattendee;
    private TextView targetname;
    private TextView timeview;
    private DatabaseReference apptdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        Intent myIntent = getIntent();
        apptnum = myIntent.getStringExtra("apptnum");
        Log.d("apptnum", "onCreate: "+apptnum);

        apptdate = (TextView)findViewById(R.id.apptdate);
        otherattendee = (TextView)findViewById(R.id.otherattendee);
        phonenum = (TextView)findViewById(R.id.phonenum);
        reason = (TextView)findViewById(R.id.reason);
        selfattendee = (TextView)findViewById(R.id.selfattendee);
        targetname = (TextView)findViewById(R.id.targetname);
        timeview = (TextView)findViewById(R.id.timeview);

        apptdatabase = FirebaseDatabase.getInstance().getReference().child("Appointments").child(apptnum);
        apptdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Date = dataSnapshot.child("date").getValue().toString();
                String OtherAttendee = dataSnapshot.child("otherAttendee").getValue().toString();
                String Phonenum = dataSnapshot.child("phone").getValue().toString();
                String Reason = dataSnapshot.child("reason").getValue().toString();
                String SelfAttendee = dataSnapshot.child("selfAttendee").getValue().toString();
                String TargetName = dataSnapshot.child("targetName").getValue().toString();
                String Time = dataSnapshot.child("time").getValue().toString();

                apptdate.setText(Date);
                otherattendee.setText(OtherAttendee);
                phonenum.setText(Phonenum);
                reason.setText(Reason);
                selfattendee.setText(SelfAttendee);
                targetname.setText(TargetName);
                timeview.setText(Time);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
