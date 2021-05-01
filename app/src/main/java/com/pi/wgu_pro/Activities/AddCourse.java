package com.pi.wgu_pro.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.R;

public class AddCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setTitle("Add Course");
    }
}