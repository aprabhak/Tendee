package com.example.fake9.tendee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateBtn;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        mEmail = (TextInputLayout)findViewById(R.id.reg_email);
        mPassword = (TextInputLayout)findViewById(R.id.reg_password);
        mCreateBtn = (Button)findViewById(R.id.reg_create_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                //Toast.makeText(RegisterActivity.this, email, Toast.LENGTH_SHORT).show();
                String password = mPassword.getEditText().getText().toString();
                //Toast.makeText(RegisterActivity.this, password, Toast.LENGTH_SHORT).show();


                register_user(email,password);
            }
        });
    }
    public boolean containsBadWords(String str)
    {
        String word = str.toLowerCase();
        if (word.contains("damn") || word.contains("shit") || word.contains("bitch") || word.contains("asshole") || word.contains("son of a bitch") || word.contains("shit hole") || word.contains("mother fucker") || word.contains("fucker") || word.contains("fuck") || word.contains("mother") || word.contains("god") || word.contains("ass") || word.contains("dick") || word.contains("suck"))
            return true;
        return false;
    }

    public boolean validDay(String str)
    {
        String[] date = str.split("/");
        if (date.length != 3)
            return false;
        int day,month, year;
        try{
        day = Integer.parseInt(date[1]);
        month = Integer.parseInt(date[0]);
        year = Integer.parseInt(date[2]);
        } catch (NumberFormatException e)
        {
            return false;
        }
        if ( 0 > day || day > 31)
            return false;
        if (month < 0 || month > 12)
            return false;
        if (year != 2018)
            return false;
        return true;
    }
    public boolean validEmail(String str)
    {
        if (!str.contains("@"))
            return false;
        String email = str.toLowerCase();
        if (email.split("@").length < 2)
            return false;
        if (email.split("@")[0].length() == 0 || email.split("@")[1].length() == 0)
            return false;
        if (email.length() < 6)
            return false;
        if (email.length() > 20)
            return false;
        if (containsBadWords(email))
            return false;
        if (!email.contains(".com"))
            return false;
        return true;
    }

    public boolean validAddress(String str)
    {
        String address = str.toLowerCase();
        if (address.length() < 10 || address.length() > 30)
            return false;
        if (containsBadWords(address))
            return false;
        if (address.contains("#") || address.contains("&") || address.contains("!")  || address.contains("$") || address.contains("%") || address.contains("^"))
            return false;
        if (!address.contains("dr"))
            return false;
        return true;
    }
    public boolean passwordAppropriate(String password)
    {
        if (password.length() > 20 || password.length() < 7)
            return false;

        int alphanumeric = 0;
        int number = 0;
        int other = 0;
        char c;
        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);
            if ( 0 <= (c - '0') && (c - '0') <= 9)
                number++;

            if ( 'a' <= (c) && (c) <= 'z')
                alphanumeric++;
            if ( 'A' <= (c) && (c) <= 'Z')
                alphanumeric++;
            if ( c == '@' || c == '#' || c == '$' || c == '*')
                other++;

        }
        if (number < 3)
            return false;
        if ( alphanumeric == 0)
            return false;
        if (other == 0)
            return false;
        return true;
    }

    public String wordFilter(String str)
    {
        if (!containsBadWords(str))
            return str;
        String newStr = "";
        String[] arr = str.split(" ");
        for (int i = 0; i < arr.length; i++) {
            if (containsBadWords(arr[i]))
            {
                String clean = "";
                for (int j = 0; j < arr[i].length(); j++) {
                    clean += "*";
                }
                if (i != 0)
                    newStr += " ";
                newStr += clean;

            }
            else
            {
                if (i != 0)
                    newStr += " ";
                newStr += arr[i];
            }
        }
        return newStr;
    }
    private void register_user(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    current_user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "verification email has been sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "verification email could not be sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid); //.getReference points to root.
                    HashMap<String,String> usermap = new HashMap<>();
                    usermap.put("email",email);
                    usermap.put("description","default");
                    mDatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent mainIntent = new Intent(RegisterActivity.this,RegisterDetailActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });


                } else {
                    Log.w("mytag", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Account could not be created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
