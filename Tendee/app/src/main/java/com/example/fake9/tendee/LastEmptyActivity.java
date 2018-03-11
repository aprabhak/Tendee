package com.example.fake9.tendee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LastEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_empty);

        final String date = getIntent().getStringExtra("APPOINTMENT_DATE");
        final String time = getIntent().getStringExtra("APPOINTMENT_TIME");
        final String attendees = getIntent().getStringExtra("ATTENDEE_NAME");
        final String target = getIntent().getStringExtra("APPOINTMENT_TARGET");
        final String myEmail = getIntent().getStringExtra("USER_EMAIL");
        final String address = getIntent().getStringExtra("ADDRESS");
        final String reason = getIntent().getStringExtra("REASON");

        Thread thread = new Thread(new Runnable(){
            public void run() {
                try {

                        GMailSender m = new GMailSender("tendeecs408@gmail.com", "cs408tendee");

                        String[] toArr = {"chaolun608@gmail.com"};
                        m.setTo(new String[]{myEmail});
                        m.setFrom("tendeecs408@gmail.com");
                        m.setSubject("Appointment Reminder");
                        m.setBody("This is to remind you that an appointment has been scheduled.\n\n" +
                                "Date: This "+date+"\n"+
                        "Time: "+time+"\n"+
                        "With: "+target+"\n"+
                        "Attendee: "+attendees+"\n"+
                        "Location: "+address+"\n"+
                                "Comments: "+reason+"\n"+
                        "\n\n"+
                        "Tendee Team");


                        try {
                            m.send();

                        } catch (Exception e) {
                            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                            Log.e("MailApp", "Could not send email", e);
                        }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        thread.interrupt();

        Intent intent = new Intent(LastEmptyActivity.this, MainActivity.class);
//                            registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();

    }
}
