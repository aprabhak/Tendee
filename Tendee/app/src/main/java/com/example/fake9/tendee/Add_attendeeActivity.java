package com.example.fake9.tendee;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Add_attendeeActivity extends AppCompatActivity {

    private ImageButton mSearchBtn;
    private EditText mSearchName;
    private RecyclerView mResultList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    public String test;
    public String date;
    public String app_time;
    public String target_name;
    public static Add_attendeeActivity app = new Add_attendeeActivity();


    public void setDate(String date){

        Add_attendeeActivity.app.date = date;
        this.date= date;
    }
    public void setTime(String time){

        Add_attendeeActivity.app.app_time = time;
        this.app_time= time;

    }
    public void setTarget(String name){

        Add_attendeeActivity.app.target_name = name;
//        Toast.makeText(Add_attendeeActivity.this, "what we have----"+name, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendee);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");



        mSearchBtn = (ImageButton) findViewById(R.id.search_bt);
        mSearchName = (EditText) findViewById(R.id.name_Input);
        mResultList = (RecyclerView) findViewById(R.id.res_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        setDate(getIntent().getStringExtra("App_Date"));
        setTime(getIntent().getStringExtra("app_Time"));
        setTarget(getIntent().getStringExtra("TARGET_NAME"));
//        Toast.makeText(Add_attendeeActivity.this, "what we have----"+app_time, Toast.LENGTH_SHORT).show();
//        Toast.makeText(Add_attendeeActivity.this, "whoops date"+date, Toast.LENGTH_SHORT).show();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String name = mSearchName.getEditText().getText().toString();

                String name = mSearchName.getText().toString();
                search_User(name);


            }
        });

    }//End of onCreate





    private void search_User(String name) {
        final Query query = mUserDatabase.orderByChild("name").startAt(name).endAt(name + "\uf8ff");
        //  Toast.makeText(Add_attendeeActivity.this, name, Toast.LENGTH_SHORT).show();

        //  Toast.makeText(Add_attendeeActivity.this, name, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerAdapter<Users, Add_attendeeActivity.UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, Add_attendeeActivity.UserViewHolder>(
                Users.class,
                R.layout.list_layout,
                Add_attendeeActivity.UserViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(Add_attendeeActivity.UserViewHolder viewHolder, Users model, int position) {
                //Toast.makeText(Add_attendeeActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
                //      Toast.makeText(Add_attendeeActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
                viewHolder.setDetails(model.getName());



            }
        };
        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    //view holder class
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public UserViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            mview.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             TextView user_name = (TextView) mview.findViewById(R.id.name_text);
                                             user_name.getText();
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, MakeAppLastStepActivity.class);

                                             String app_date  = app.date;
                                             String app_time = app.app_time;

                                             intent.putExtra("ATTENDEE_NAME", user_name.getText());
                                             intent.putExtra("APPOINTMENT_DATE", app_date);
                                             intent.putExtra("APPOINTMENT_TIME", app.app_time);
                                             intent.putExtra("APPOINTMENT_TARGET", app.target_name);
                                          //   Log.d("tallll:","jhgh"+app_time);
                                           //  Log.d("hello", "onClick: ");
                                            // Toast.makeText(Add_attendeeActivity.this,"",Toast.LENGTH_SHORT).show();
                                             context.startActivity(intent);


                                              // Call once you redirect to another activity

                                         }
                                     }


            );

        }



        public void setDetails(String username) {
            TextView user_name = (TextView) mview.findViewById(R.id.name_text);
            // ImageView user_image = (ImageView) mview.findViewById(R.id.imageView);
            user_name.setText(username);


        }


    }


}
