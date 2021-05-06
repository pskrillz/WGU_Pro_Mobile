package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Note;
import com.pi.wgu_pro.R;

public class NoteDetails extends AppCompatActivity {

    //vars
    Database db;
    boolean noteUpdated = false;
    Intent intent;
    int courseId;
    int noteId;
    Note theNote;

    // elements
    EditText ndTitle;
    EditText ndBody;
    Button ndSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        getSupportActionBar().setTitle("Note Details");

        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);
        noteId = intent.getIntExtra("noteId", noteId);
        theNote = db.noteDao().getSpecNote(noteId);
        ndTitle = findViewById(R.id.ndTitle);
        ndBody = findViewById(R.id.ndBody);
        ndSaveBtn = findViewById(R.id.ndSaveBtn);

        initNoteDetails();

        ndSaveBtn.setOnClickListener(v -> {
            saveNote();
            if(noteUpdated == true) {
                Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }

    private void initNoteDetails() {

        ndTitle.setText(theNote.getNoteTitle());
        ndBody.setText(theNote.getNoteContent());
    }

    private void saveNote() {

        theNote.setNoteTitle(ndTitle.getText().toString());
        theNote.setNoteContent(ndBody.getText().toString());
        db.noteDao().updateNote(theNote);
        noteUpdated = true;
    }


}