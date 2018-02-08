package com.example.fake9.tendee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterDetailActivity extends AppCompatActivity {

    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mAddress;
    private Button mDone;

    private Toolbar mToolbar;

    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail);

        mToolbar = findViewById(R.id.registerdetail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Details");

        mName = (TextInputLayout)findViewById(R.id.regdetail_name);
        mDescription = (TextInputLayout)findViewById(R.id.regdetail_description);
        mAddress = (TextInputLayout)findViewById(R.id.regdetail_address);
        mDone = (Button)findViewById(R.id.regdetail_done_btn);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getEditText().getText().toString();
                String description = mDescription.getEditText().getText().toString();
                String address = mAddress.getEditText().getText().toString();
                add_detail_to_user(name,description,address);

            }
        });

    }

    private void add_detail_to_user(String name, String description, String address) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);//.getReference points to root.
        HashMap<String,Object> usermap = new HashMap<>();
        usermap.put("name",name);
        usermap.put("description",description);
        usermap.put("address",address);
        mDatabase.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent registerIntent = new Intent(RegisterDetailActivity.this,MainActivity.class);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(registerIntent);
                    finish();
                }
            }
        });
    }
}
