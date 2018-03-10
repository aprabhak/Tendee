package com.example.fake9.tendee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    public boolean validName(String name)
    {
        if (name.length() < 5)
            return false;
        if (!(name.charAt(0) <= 'Z' && name.charAt(0) >= 'A'))
            return false;

        if (name.split(" ").length < 2)
            return false;


        if (name.contains("#") || name.contains("&") || name.contains("!")  || name.contains("$") || name.contains("%") || name.contains("^"))
            return false;

        return true;
    }

    public boolean containsBadWords(String str)
    {
        String word = str.toLowerCase();
        if (word.contains("bitch") || word.contains("asshole") || word.contains("son of a bitch") || word.contains("shit hole") || word.contains("mother fucker") || word.contains("fucker") || word.contains("fuck") || word.contains("mother") || word.contains("god") || word.contains("ass") || word.contains("dick") || word.contains("suck"))
            return true;
        return false;
    }

    public boolean validDescription(String str)
    {
        String string = str.toLowerCase();
        if (string.length() < 4)
            return false;
        if (string.length() > 50)
            return false;
        if (containsBadWords(string))
            return false;

        if (string.contains("#") || string.contains("&")  || string.contains("$") || string.contains("%") || string.contains("^"))
            return false;
        return true;
    }
    private void add_detail_to_user(String name, String description, String address) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);//.getReference points to root.
        HashMap<String,Object> usermap = new HashMap<>();
        usermap.put("name",name);
        usermap.put("description",description);
        usermap.put("address",address);
        Map<String,ArrayList<Long>> week = new HashMap<>();
        week.put("Monday",new ArrayList<Long>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)));
        week.put("Tuesday",new ArrayList<Long>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)));
        week.put("Wednesday",new ArrayList<Long>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)));
        week.put("Thursday",new ArrayList<Long>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)));
        week.put("Friday",new ArrayList<Long>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)));
        //ArrayList<Integer> test = week.get("Monday");
        //test.add(2,5);
        //week.put("Monday",test);
        //int i = test.get(2);
        //Log.d("fdgfd","value " +i);
        usermap.put("week",week);
        int min = 1;
        int max = 100000;
        int randomNum = ThreadLocalRandom.current().nextInt(min,max+1);
        usermap.put("Id",randomNum);

        usermap.put("blockList",new ArrayList<String>(Arrays.asList(" ", " " ," "," "," ")));
        mDatabase.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().signOut();
                    Intent registerIntent = new Intent(RegisterDetailActivity.this,MainActivity.class);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(registerIntent);
                    finish();
                }
            }
        });
    }
}
