package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private TextView mName;
    private TextView mEmail;
    private TextView mDescription;
    private TextView mAddress;
    private TextView mEditDescription;
    private TextView mEditAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mName = (TextView)findViewById(R.id.settings_display_name);
        mEmail = (TextView)findViewById(R.id.settings_email);
        mDescription = (TextView)findViewById(R.id.settings_description);
        mAddress = (TextView)findViewById(R.id.settings_address);
        mEditDescription = (TextView)findViewById(R.id.settings_edit_description);
        mEditAddress = (TextView)findViewById(R.id.settings_edit_address);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String description = dataSnapshot.child("description").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();

                mName.setText(name);
                mEmail.setText(email);
                mDescription.setText(description);
                mAddress.setText(address);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettingsActivity.this,EditSettingsActivity.class);
                editIntent.putExtra("edit","description");
                startActivity(editIntent);
            }
        });

        mEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(SettingsActivity.this,EditSettingsActivity.class);
                editIntent.putExtra("edit","address");
                startActivity(editIntent);
            }
        });

    }
}
