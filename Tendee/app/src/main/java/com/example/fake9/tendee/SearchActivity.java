package com.example.fake9.tendee;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Properties;


public class SearchActivity extends AppCompatActivity {
    private ImageButton mSearchBtn;
    private EditText mSearchName;
    private RecyclerView mResultList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mFirstDatabase;
    private FirebaseUser mCurrentUser;
    public String name_currentUser;
    public static SearchActivity sea = new SearchActivity();

    private static final String Admin_Address = "tendee408@outlook.com";
    private static final String Admin_Password = "cs408tendee";
    private static final String Outlook_MailServer = "smtp.office365.com";
    private static final String Mail_Port = "587";


    public void getname(String name) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchBtn = (ImageButton) findViewById(R.id.imageButton);
        mSearchName = (EditText) findViewById(R.id.Search_Input);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();

        mFirstDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mFirstDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //executes when data retrieved.
                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                sea.name_currentUser= dataSnapshot.child("name").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String name = mSearchName.getEditText().getText().toString();

                String name = mSearchName.getText().toString();
                search_User(name);
                /**
                if(name.length() >= 2){

                }else{
                    Toast.makeText(SearchActivity.this,"Not Enough Information", Toast.LENGTH_SHORT).show();
                }
                */



            }
        });

    }//End of onCreate


    private void search_User(String name) {
        final Query query = mUserDatabase.orderByChild("name").startAt(name).endAt(name + "\uf8ff");
        //  Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();

        //  Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerAdapter<Users, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(
                Users.class,
                R.layout.list_layout,
                UserViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, Users model, int position) {
                //Toast.makeText(SearchActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
                //      Toast.makeText(SearchActivity.this, model.getName(), Toast.LENGTH_SHORT).show();

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

                                                 Context context = view.getContext();
                                                 Intent intent = new Intent(context, Search_ResultActivity.class);
//                                             Toast.makeText(context, user_name.getText(), Toast.LENGTH_SHORT).show();
                                                 intent.putExtra("User_Name", user_name.getText());
                                                 context.startActivity(intent);


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
