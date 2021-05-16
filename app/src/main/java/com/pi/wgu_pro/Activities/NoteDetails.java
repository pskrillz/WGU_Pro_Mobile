package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                backToCourseDetails();
            }
        });
    }

    private void backToCourseDetails() {
        Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
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


    // menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_note_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ndDelete:
                db.noteDao().deleteNote(theNote);
                Toast.makeText(this, "Deleted note title: " + theNote.getNoteTitle() , Toast.LENGTH_SHORT).show();
                backToCourseDetails();
                return true;
            case R.id.ndShare:
                smsSendMessage();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void smsSendMessage() {
        String msg = "Shared Note, Title: " + theNote.getNoteTitle() + ", Content: " + theNote.getNoteContent();

        String smsNumber = "smsto:9493743490";
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body", msg);

        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }



}