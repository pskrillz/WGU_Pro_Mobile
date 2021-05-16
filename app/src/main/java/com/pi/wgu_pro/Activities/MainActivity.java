package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.ReminderBroadcast;

public class MainActivity extends AppCompatActivity {

    private Button enterBtn;
    Button mNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().setTitle("WGU Pro");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);






        mNotify = findViewById(R.id.mNotify);
        enterBtn = findViewById(R.id.enterBtn);

/**
 * Setting up event handler for button click to switch activity.
 */
       enterBtn.setOnClickListener(view -> launchActivity());


       mNotify.setOnClickListener(v -> {
           testNotifiction();
       });

    } // end on create

    private void testNotifiction() {

        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecondsInMilis = 1000 * 5;
        ReminderBroadcast.setAlert(this, "alerts", 12,
                timeAtButtonClick + tenSecondsInMilis, "WOW", "You did it");

    }


    private void launchActivity() {

        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }


} // end main class