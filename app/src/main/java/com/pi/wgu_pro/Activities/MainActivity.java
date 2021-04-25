package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.R;

public class MainActivity extends AppCompatActivity {

    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().setTitle("WGU Pro");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);



        enterBtn = (Button) findViewById(R.id.enterBtn);

/**
 * Setting up event handler for button click to switch activity.
 */
       enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

    }


    private void launchActivity() {

        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

}