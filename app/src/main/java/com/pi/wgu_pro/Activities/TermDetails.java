package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    Term selTerm;
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
        selTerm = db.termDao().getSpecTerm(termId);


        // view elements
        tdTitle = findViewById(R.id.cdTitle);
        tdStatus = findViewById(R.id.cdStatus);
        tdStart = findViewById(R.id.cdStart);
        tdEnd = findViewById(R.id.cdEnd);
        tdAddCourse = findViewById(R.id.tdAddCourse);
        rv = findViewById(R.id.rvAssessList);

        // init
        setupSpecTermDetails();
        // updateCourseRv()
        initCourseRv();

        tdAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddCourse.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
        });

    } // end on create


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

    // menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_term_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tdDeleteBtn:
                // validation for deleting a term that has courses.
                if(!(db.courseDao().getTermCourses(termId).isEmpty())){
                    Toast.makeText(this, "Cannot delete term that contains courses" , Toast.LENGTH_SHORT).show();
                    return false;
                }

                db.termDao().deleteTerm(selTerm);
                Toast.makeText(this, "Deleted " + selTerm.getTermName() , Toast.LENGTH_SHORT).show();
                openTermList();
                return true;
            case R.id.tdEditBtn:
                openEditTerm();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openEditTerm(){
        Intent intent = new Intent(getApplicationContext(), EditTerm.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    public void openTermList(){
        Intent intent = new Intent(getApplicationContext(), TermList.class);
        startActivity(intent);
    }




}