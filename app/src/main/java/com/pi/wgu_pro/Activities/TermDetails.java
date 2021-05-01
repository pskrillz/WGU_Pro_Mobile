package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pi.wgu_pro.Adapters.CourseAdapter;
import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.R;

import java.util.List;

public class TermDetails extends AppCompatActivity {
    // vars
    Intent intent;
    Database db;
    int termId;
    List<Course> coursesList;

    // view elements
    FloatingActionButton tdAddCourse;
    RecyclerView rv;
    TextView tdTitle;
    TextView tdStatus;
    TextView tdStart;
    TextView tdEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setTitle("Term Details");

        // vars
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        db = Database.getInstance(getApplicationContext());
        coursesList = db.courseDao().getTermCourses(termId);

        // view elements
        tdTitle = findViewById(R.id.tdTitle);
        tdStatus = findViewById(R.id.tdStatus);
        tdStart = findViewById(R.id.tdStart);
        tdEnd = findViewById(R.id.tdEnd);
        tdAddCourse = findViewById(R.id.tdAddCourse);
        rv = findViewById(R.id.rvCourseList);

        // init
        setupSpecTermDetails();
        // updateCourseRv()
        initCourseRv();

        tdAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddCourse.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
        });

    }

    private void setupSpecTermDetails() {
        Term term = new Term();
        term = db.termDao().getSpecTerm(termId);
        String title = term.getTermName();
        String startDate = DateFormat.format("MM/dd/yyyy", term.getTermStart()).toString();
        String endDate = DateFormat.format("MM/dd/yyyy", term.getTermEnd()).toString();

        tdTitle.setText(title);
        tdStatus.setText(term.getTermStatus());
        tdStart.setText(startDate);
        tdEnd.setText(endDate);
    }

    private void initCourseRv(){
        CourseAdapter adapter = new CourseAdapter(coursesList, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}