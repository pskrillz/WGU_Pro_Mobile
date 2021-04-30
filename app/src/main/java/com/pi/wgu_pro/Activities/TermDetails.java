package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.R;

import java.util.List;

public class TermDetails extends AppCompatActivity {
    Intent intent;
    Database db;
    int termId;
    FloatingActionButton tdAddCourse;
    List<Course> coursesList;
    RecyclerView rvCourseList;
    TextView tdTitle;
    TextView tdStatus;
    TextView tdStart;
    TextView tdEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setTitle("Term Details");

        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        db = Database.getInstance(getApplicationContext());
        tdTitle = findViewById(R.id.tdTitle);

        setupSpecTermDetails();

    }

    private void setupSpecTermDetails() {
        Term term = new Term();
        term = db.termDao().getSpecTerm(termId);
        String title = term.getTermName();

        tdTitle.setText(title);
    }
}