package com.example.fake9.tendee;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout mEdit;
    private Button mEditBtn;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mToolbar = (Toolbar)findViewById(R.id.edit_settings_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEdit = (TextInputLayout)findViewById(R.id.edit_input);
        mEditBtn = (Button)findViewById(R.id.edit_btn);

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit = mEdit.getEditText().getText().toString();
                Intent myIntent = getIntent();
                String key = myIntent.getStringExtra("edit");
                if (key.equals("description")) {
                    //Toast.makeText(this, "okay!", Toast.LENGTH_SHORT).show();
                    mUserDatabase.child("description").setValue(edit);
                }
                if (key.equals("address")) {
                    //Toast.makeText(this, "also okay!", Toast.LENGTH_SHORT).show();
                    mUserDatabase.child("address").setValue(edit);
                }
            }
        });



    }
}
