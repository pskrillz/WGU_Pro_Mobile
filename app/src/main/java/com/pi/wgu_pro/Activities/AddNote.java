package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Note;
import com.pi.wgu_pro.R;

public class AddNote extends AppCompatActivity {

    //vars
    Database db;
    boolean noteAdded = false;
    Intent intent;
    int courseId;

    // elements
    EditText anTitle;
    EditText anBody;
    Button anSaveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().setTitle("Add Note");

        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        anTitle = findViewById(R.id.anTitle);
        anBody = findViewById(R.id.anBody);
        anSaveBtn = findViewById(R.id.anSaveBtn);

        anSaveBtn.setOnClickListener(v -> {
            saveNote();
            if(noteAdded == true) {
                Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });




    }

    private void saveNote() {
        Note note = new Note(courseId, anTitle.getText().toString(), anBody.getText().toString());
        db.noteDao().insertNote(note);
        noteAdded = true;
    }


}