package com.example.fake9.tendee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
    private Button mEditPassword;

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
        mEditPassword = (Button)findViewById(R.id.EditPassBtn);

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

        mEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SettingsActivity.this, "yoyo", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.change_password,null);
                final EditText currentPassword = (EditText)mView.findViewById(R.id.edcurrPassword);
                final EditText newPassword = (EditText)mView.findViewById(R.id.ednewPassword);
                final EditText newPassword2 = (EditText)mView.findViewById(R.id.ednewPassword2);
                Button changePassBtn = (Button)mView.findViewById(R.id.changePassword);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                changePassBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currentPass = currentPassword.getText().toString();
                        final String newPass = newPassword.getText().toString();
                        String newPass2 = newPassword2.getText().toString();
                        if (!newPass.equals(newPass2)) {
                            Toast.makeText(SettingsActivity.this, "new passwords do not match", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        /*user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this, "password updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                        String email = user.getEmail();
                        AuthCredential credential = EmailAuthProvider.getCredential(email,currentPass);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(SettingsActivity.this, "reauthenticated", Toast.LENGTH_SHORT).show();
                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SettingsActivity.this, "password changed", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(SettingsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(SettingsActivity.this, "current password is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
