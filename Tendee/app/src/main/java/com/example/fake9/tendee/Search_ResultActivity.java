package com.example.fake9.tendee;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Search_ResultActivity extends AppCompatActivity {
    private Button mMakeappBtm;
    private TextView mResName;
    private TextView mResDes;
    private TextView mResAddress;
    private TextView mResEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    public String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_user);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mResName = (TextView)findViewById(R.id.result_name);
        mResDes = (TextView)findViewById(R.id.result_desc);
        mResAddress = (TextView)findViewById(R.id.result_address);
        mResEmail = (TextView)findViewById(R.id.result_email);
        mMakeappBtm = (Button)findViewById(R.id.MkapmtBtm);

        final String name =getIntent().getStringExtra("User_Name");
        Toast.makeText(Search_ResultActivity.this, name, Toast.LENGTH_SHORT).show();
        mResName.setText(name);

//        mUserDatabase.child("name").equalTo(name);
//        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query =  mUserDatabase.orderByChild("name").equalTo(name);//ok
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(Search_ResultActivity.this, Long.toString(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    if(childSnapshot.exists()) {
                        Users user = childSnapshot.getValue(Users.class);
                        //   String address = dataSnapshot.getRef().ge;
                        mResName.setText(user.getName());
                        mResAddress.setText(user.getAddress());
                        mResDes.setText(user.getDescription());
                        mResEmail.setText(user.getEmail());
                    break;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Search_ResultActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });

        mMakeappBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String name = mSearchName.getEditText().getText().toString();
                Context context = view.getContext();
                Intent intent = new Intent(context, Make_AppointmentActivity.class);
//                Toast.makeText(context, user_name.getText(), Toast.LENGTH_SHORT).show();
//                intent.putExtra("User_Name", user_name.getText());
                context.startActivity(intent);

            }
        });


    }
}
