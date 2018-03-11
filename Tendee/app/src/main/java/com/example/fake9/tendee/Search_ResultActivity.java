package com.example.fake9.tendee;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Search_ResultActivity extends AppCompatActivity {
    private ImageButton mMakeappBtm;
    private Switch mBlockSwitch;
    private TextView mResName;
    private TextView mResDes;
    private TextView mResAddress;
    private TextView mResEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mTempDatabase;
    private FirebaseUser mCurrentUser;
    public String res_user_email;
    public String current_user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_user);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mTempDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mResName = (TextView) findViewById(R.id.result_name);
        mResDes = (TextView) findViewById(R.id.result_desc);
        mResAddress = (TextView) findViewById(R.id.result_address);
        mResEmail = (TextView) findViewById(R.id.result_email);
        mMakeappBtm = (ImageButton) findViewById(R.id.MkapmtBtm);
        mBlockSwitch = (Switch) findViewById(R.id.block_switch);
        mResDes.setMovementMethod(new ScrollingMovementMethod());
        mBlockSwitch.setTextOff("OFF");
        mBlockSwitch.setTextOn("ON");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        final String name = getIntent().getStringExtra("User_Name");
//        Toast.makeText(Search_ResultActivity.this, name, Toast.LENGTH_SHORT).show();
        mResName.setText(name);
        Query query = mUserDatabase.orderByChild("name").equalTo(name);//ok
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(Search_ResultActivity.this, Long.toString(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (childSnapshot.exists()) {
                        Users user = childSnapshot.getValue(Users.class);
                        //   String address = dataSnapshot.getRef().ge;
                        res_user_email = user.getEmail();
                        mResName.setText(user.getName());
                        mResAddress.setText(user.getAddress());
                        mResDes.setText(user.getDescription());
                        mResEmail.setText(user.getEmail());

                        mTempDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("blockList");
                        mTempDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                                for (DataSnapshot data : dataSnapshot.getChildren()) {


                                    if (data.getValue().toString().equals(res_user_email)) {
                                        mBlockSwitch.setChecked(true);
                                    }

                                }



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Search_ResultActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });



        mTempDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mTempDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                current_user_email = dataSnapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mBlockSwitch.setChecked(true);

//
//        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//        Query setDefaultCheckQuery = mUserDatabase.orderByChild("email").equalTo(mResEmail.getText().toString());//ok
//        setDefaultCheckQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                    childSnapshot = childSnapshot.child("blockList");
//
//                    for (int i = 0; i <= 4; i++) {
//                        String email = childSnapshot.child(Integer.toString(i)).getValue().toString();
//                        if (email.equals(current_user_email)) {
//                            Toast.makeText(Search_ResultActivity.this, "Onclick->>>> ", Toast.LENGTH_SHORT).show();
//                            mBlockSwitch.setChecked(true);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(Search_ResultActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
//            }
//        });










        mBlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

                mTempDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("blockList");
                mTempDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                        //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                        if (mBlockSwitch.isChecked()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data.getValue().equals(" ")) {
                                    data.getRef().setValue(res_user_email);
                                    return;
                                }

                            }
                        } else {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data.getValue().equals(res_user_email)) {
                                    data.getRef().setValue(" ");

//                                   Log.d("TAG", "onDataChange: "+mResEmail);

                                    return;
                                }

                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });




//        mUserDatabase.child("name").equalTo(name);
//        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();



        mMakeappBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //   String name = mSearchName.getEditText().getText().toString();
//                Toast.makeText(Search_ResultActivity.this, "Onclick->>>> ", Toast.LENGTH_SHORT).show();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                Query query = mUserDatabase.orderByChild("email").equalTo(mResEmail.getText().toString());//ok
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean findBlockedUser=false;
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

//                            = childSnapshot.child("address").getValue().toString();
                            childSnapshot = childSnapshot.child("blockList");

                            for (int i = 0; i <= 4; i++) {
                                String email = childSnapshot.child(Integer.toString(i)).getValue().toString();
                                if (email.equals(current_user_email)) {
                                    Toast.makeText(Search_ResultActivity.this, "You are blocked", Toast.LENGTH_SHORT).show();

                                findBlockedUser=true;
                                    Intent intent = new Intent(Search_ResultActivity.this, MainActivity.class);

                                    intent.putExtra("target_user_name", mResName.getText());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        if (!findBlockedUser) {

                            Context context = view.getContext();
                            Intent intent = new Intent(context, Make_AppointmentActivity.class);

                            intent.putExtra("target_user_name", mResName.getText());
                            context.startActivity(intent);
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Search_ResultActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });


    }
}
