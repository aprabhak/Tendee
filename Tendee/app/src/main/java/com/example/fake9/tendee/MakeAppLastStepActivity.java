package com.example.fake9.tendee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;


public class MakeAppLastStepActivity extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private DatabaseReference maddressdatabase;
    private DatabaseReference mDatabase;
    private static final String Admin_Address = "tendee408@outlook.com";
    private static final String Admin_Password = "cs408tendee";
    private static final String Outlook_MailServer = "smtp.office365.com";
    private static final String Mail_Port = "587";
    private TextView display_attendee;
    private TextView display_date;
    private TextView display_time;
    private EditText input_phone;
    private EditText input_reason;
    private Button mFinish;
    private ImageButton mAddMoreAttendees;
    public String attendee_list;
    public String self_name;
    public String other_attendee_name;
    public String target_name;
    public String date = "empty";
    public String app_time = "empty";
    public String reason;
    public String phone;
    public String myEmail;
    public String myAddress;
    public String targetAddress;
    final int randomNum = (int) ((Math.random() * ((900000000 - 100000000) + 1)) + 100000000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_app_last_step);



        display_date = findViewById(R.id.date_display);
        display_time = findViewById(R.id.time_display);
        display_attendee = findViewById(R.id.attendee_display);
        mFinish = findViewById(R.id.finish_btn);
        input_phone = findViewById(R.id.phone_input);
        input_reason = findViewById(R.id.reason_input);
        date = getIntent().getStringExtra("APPOINTMENT_DATE");
        app_time = getIntent().getStringExtra("APPOINTMENT_TIME");
        other_attendee_name = getIntent().getStringExtra("ATTENDEE_NAME");
        target_name = getIntent().getStringExtra("APPOINTMENT_TARGET");


//        Toast.makeText(MakeAppLastStepActivity.this,"target_name"+target_name, Toast.LENGTH_SHORT).show();


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                String index = parseTime(app_time);
                self_name = dataSnapshot.child("name").getValue().toString();
                myEmail=dataSnapshot.child("email").getValue().toString();
                myAddress = dataSnapshot.child("address").getValue().toString();
//                dataSnapshot.child("week").child(date).child(index).getRef().setValue(randomNum);


                if (other_attendee_name.equals(" ")) {
                    attendee_list=self_name;
                }else {
                    attendee_list = other_attendee_name + ", " + self_name;
                }
                    display_attendee.setText(attendee_list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        maddressdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = maddressdatabase.orderByChild("name").equalTo(target_name);//ok
//        Toast.makeText(MakeAppLastStepActivity.this, "targetAddress----- is"+target_name, Toast.LENGTH_SHORT).show();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(Search_ResultActivity.this, Long.toString(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {


                        targetAddress= childSnapshot.child("address").getValue().toString();
//                        Toast.makeText(MakeAppLastStepActivity.this, "targetAddress is"+targetAddress, Toast.LENGTH_SHORT).show();

                        break;


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MakeAppLastStepActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });

//        for (DataSnapshot data1 : dataSnapshot.getChildren()) {
//            if (data1.child("name").getValue().equals(target_name)) {
//                //do ur stuff
//                targetAddress = data.child("address").getValue().toString();
//                Toast.makeText(MakeAppLastStepActivity.this,"target is "+targetAddress, Toast.LENGTH_SHORT).show();
//
//            }
//        }



        display_date.setText(date);
        display_time.setText(app_time);

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = input_phone.getText().toString();
                reason = input_reason.getText().toString();
                if (containsBadWords(reason)){
                     Toast.makeText(MakeAppLastStepActivity.this,"Language!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (reason.equals("") || phone == null){
                    Toast.makeText(MakeAppLastStepActivity.this,"Information Incomplete!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (reason.length()>60){
                    Toast.makeText(MakeAppLastStepActivity.this,"No more than 60 characters",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length()>15){
                    Toast.makeText(MakeAppLastStepActivity.this,"No more than 15 numbers",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length()<3){
                    Toast.makeText(MakeAppLastStepActivity.this,"Incorrect Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

//                Toast.makeText(MakeAppLastStepActivity.this,
//                        target_name+date+","+app_time+","+reason+","+phone, Toast.LENGTH_SHORT).show();

//                FirebaseDatabase.getInstance().getReference().child("Appointments").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // get total available quest
//                        num_app[0] = dataSnapshot.getChildrenCount();
//Toast.makeText(MakeAppLastStepActivity.this,"count is"+num_app[0]+"",Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });


//                Toast.makeText(MakeAppLastStepActivity.this,
//                        target_name+date+","+app_time+","+reason+","+phone, Toast.LENGTH_SHORT).show();

//                FirebaseDatabase.getInstance().getReference().child("Appointments").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // get total available quest
//                        num_app[0] = dataSnapshot.getChildrenCount();
//Toast.makeText(MakeAppLastStepActivity.this,"count is"+num_app[0]+"",Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                final String index = parseTime(app_time);


                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                    Toast.makeText(MakeAppLastStepActivity.this,"i am in"+data.child("name").getValue(),Toast.LENGTH_SHORT).show();

                            if (data.child("name").getValue().equals(other_attendee_name) || other_attendee_name.equals(" ")) {
                                //do ur stuff
//                                Toast.makeText(MakeAppLastStepActivity.this, "i am in" + date, Toast.LENGTH_SHORT).show();


                                DataSnapshot a = data.child("week").child(date).child(index);
                                if (other_attendee_name.equals(" ")|Integer.parseInt(a.getValue().toString()) == 1 || Integer.parseInt(a.getValue().toString()) == 0) {


                                    if (!other_attendee_name.equals(" ")) {
                                        a.getRef().setValue(randomNum);
                                        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                                                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                                                String index = parseTime(app_time);
                                                dataSnapshot.child("week").child(date).child(index).getRef().setValue(randomNum);

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }


//                                    Toast.makeText(MakeAppLastStepActivity.this,"target is "+targetAddress, Toast.LENGTH_SHORT).show();
                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Appointments").child(randomNum + "");

                                    HashMap<String, Object> app_map = new HashMap<>();
                                    app_map.put("targetName", target_name);
                                    app_map.put("reason", reason);
                                    app_map.put("phone", phone);
                                    app_map.put("date", date);
                                    app_map.put("time", app_time);
                                    app_map.put("selfAttendee", self_name);
                                    app_map.put("otherAttendee", other_attendee_name);
                                    app_map.put("address", targetAddress);


                                    mDatabase.updateChildren(app_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                    if (data.child("name").getValue().equals(target_name)) {
                                                        //do ur stuff
                                                        DataSnapshot a = data.child("week").child(date).child(index);
                                                        if (Integer.parseInt(a.getValue().toString()) == 1 || Integer.parseInt(a.getValue().toString()) == 0) {
                                                            a.getRef().setValue(randomNum);
                                                        }

                                                    }
                                                }

                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                    if (data.child("name").getValue().equals(self_name)) {
                                                        //do ur stuff
                                                        DataSnapshot a = data.child("week").child(date).child(index);
                                                        if (Integer.parseInt(a.getValue().toString()) == 1 || Integer.parseInt(a.getValue().toString()) == 0) {
                                                            a.getRef().setValue(randomNum);
                                                        }

                                                    }
                                                }



                                                Toast.makeText(MakeAppLastStepActivity.this, "SUCCESS" , Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(MakeAppLastStepActivity.this, LastEmptyActivity.class);
//                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                intent.putExtra("ATTENDEE_NAME", self_name+"      "+other_attendee_name);
                                                intent.putExtra("APPOINTMENT_DATE", date);
                                                intent.putExtra("APPOINTMENT_TIME",app_time);
                                                intent.putExtra("APPOINTMENT_TARGET", target_name);
                                                intent.putExtra("USER_EMAIL",myEmail );
                                                intent.putExtra("ADDRESS",targetAddress );
                                                intent.putExtra("REASON",reason );
                                                startActivity(intent);
                                                finish();

                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(MakeAppLastStepActivity.this, "Other Attendee is no free:" + a.getValue(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MakeAppLastStepActivity.this, MainActivity.class);
//                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                    finish();
                                }
//                                        Toast.makeText(MakeAppLastStepActivity.this,"________yes__________"+data.child("week").child(date).child(index+"").getValue(),Toast.LENGTH_SHORT).show();
                            } else {
                                //do something
                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }


        });
        //Toast.makeText(MakeAppLastStepActivity.this, date + "---" + attendee_list, Toast.LENGTH_SHORT).show();
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

    public boolean containsBadWords(String str)
    {
        String word = str.toLowerCase();
        if (word.contains("damn") || word.contains("shit") || word.contains("bitch") || word.contains("asshole") || word.contains("son of a bitch") || word.contains("shit hole") || word.contains("mother fucker") || word.contains("fucker") || word.contains("fuck") || word.contains("mother") || word.contains("god") || word.contains("ass") || word.contains("dick") || word.contains("suck"))
            return true;
        return false;
    }



}
