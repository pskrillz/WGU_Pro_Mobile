package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Instructor;
import com.pi.wgu_pro.R;

public class AddInstructor extends AppCompatActivity {

    // vars
    Database db;
    Intent intent;
    int courseId;
    Instructor selInstructor;
    boolean existingInstructor;

    //views
    EditText aiName;
    EditText aiPhone;
    EditText aiEmail;
    Button aiSaveBtn;
    TextView aiNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        getSupportActionBar().setTitle("Instructor Details");

        //vars
        // var init
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        aiName = findViewById(R.id.aiName);
        aiPhone = findViewById(R.id.aiPhone);
        aiEmail = findViewById(R.id.aiEmail);
        aiSaveBtn = findViewById(R.id.aiSaveBtn);
        aiNotice = findViewById(R.id.aiNotice);


        if(!(db.instructorDao().getCourseInstructors(courseId).isEmpty())){
            existingInstructor = true;
            selInstructor = db.instructorDao().getCourseInstructors(courseId).get(0);
            initValues();
        } else {
            existingInstructor = false;
            aiNotice.setText("Instructor not yet added");
        }

        aiSaveBtn.setOnClickListener(v -> {
            if(existingInstructor){
                saveUpdate();
            } else {
                addNewInstructor();
            }
        });


    } // oncreate

    public void initValues(){
        aiNotice.setText("Instructor Found");
        aiName.setText(selInstructor.getName());
        aiPhone.setText(selInstructor.getPhone());
        aiEmail.setText(selInstructor.getEmail());
    }


    public void saveUpdate(){
        selInstructor.setName(aiName.getText().toString());
        selInstructor.setEmail(aiEmail.getText().toString());
        selInstructor.setPhone(aiPhone.getText().toString());
        selInstructor.setCourseIdFK(courseId);
        db.instructorDao().updateInstructor(selInstructor);
        Toast.makeText(this, "Instructor Updated", Toast.LENGTH_SHORT).show();
        backToCourseDetails();

    }

    public void addNewInstructor(){
        Instructor newInstructor = new Instructor();
        newInstructor.setName(aiName.getText().toString());
        newInstructor.setPhone(aiPhone.getText().toString());
        newInstructor.setEmail(aiEmail.getText().toString());
        newInstructor.setCourseIdFK(courseId);
        db.instructorDao().insertInstructor(newInstructor);
        Toast.makeText(this, "Instructor Added", Toast.LENGTH_SHORT).show();
        aiNotice.setText("Instructor Added");
        backToCourseDetails();

    }


    public void backToCourseDetails(){
        Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

} // class