package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pi.wgu_pro.Adapters.AssessmentAdapter;
import com.pi.wgu_pro.Adapters.NoteAdapter;
import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Assessment;
import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.Entities.Note;
import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.MiscSingleton;

import java.util.List;

public class CourseDetails extends AppCompatActivity {
    // vars
    Database db;
    Intent intent;
    int courseId;
    List<Assessment> assessmentList;
    List<Note> noteList;

    // views
    TextView cdTitle;
    TextView cdStatus;
    TextView cdStart;
    TextView cdEnd;
    ImageButton cdInstructorBtn;
    Switch cdAlertSwitch;
    RecyclerView rvAssessList;
    RecyclerView rvNoteList;
    FloatingActionButton cdAddAssFab;
    FloatingActionButton cdAddNoteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setTitle("Course Details");

        // var init
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);
        assessmentList = db.assessmentDao().getCourseAssessments(courseId);
        noteList = db.noteDao().getCourseNotes(courseId);

        // views
        cdTitle = findViewById(R.id.cdTitle);
        cdStatus = findViewById(R.id.cdStatus);
        cdStart = findViewById(R.id.cdStart);
        cdEnd = findViewById(R.id.cdEnd);
        cdInstructorBtn = findViewById(R.id.cdInstructorBtn);
        cdAlertSwitch = findViewById(R.id.cdAlertSwitch);
        rvAssessList = findViewById(R.id.rvAssessList);
        rvNoteList = findViewById(R.id.rvNotesList);
        cdAddAssFab = findViewById(R.id.cdAddAssFab);
        cdAddNoteFab = findViewById(R.id.cdAddNoteFab);

        cdAddAssFab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddAssessment.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        });

        cdAddNoteFab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddNote.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        });

        // function calls
        initCourseDetails();
        initNoteRv();
        initAssessmentRv();



    } // onCreate end

    public void initCourseDetails(){
        Course course = db.courseDao().getSpecCourse(courseId);
        cdTitle.setText(course.getCourseName());
        cdStatus.setText(course.getCourseStatus());
        cdStart.setText(MiscSingleton.formatDateStr(course.getCourseStart()));
        cdEnd.setText(MiscSingleton.formatDateStr(course.getCourseEnd()));
        if (course.getCourseAlert()) {
            cdAlertSwitch.setChecked(true);
        } else {
            cdAlertSwitch.setChecked(false);
        }
    }

    public void initNoteRv(){
        RecyclerView rv = rvNoteList;
        NoteAdapter adapter = new NoteAdapter(noteList, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initAssessmentRv(){
        RecyclerView rv = rvAssessList;
        AssessmentAdapter adapter = new AssessmentAdapter(assessmentList, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }







}// class end