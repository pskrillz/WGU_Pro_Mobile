package com.pi.wgu_pro.DB;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pi.wgu_pro.Entities.Note;

import java.util.List;

public interface NoteDao {

    // get all Notes
    @Query("select * from tableNote")
    List<Note> getAllNotes();

    // get a specific course's list of notes
    @Query("select * from tableNote where courseIdFK = :courseId")
    List<Note> getCourseNotes(int courseId);

    //get specific note
    @Query("select * from tableNote where noteId = :noteId")
    Note getSpecNote(int noteId);

    // add instructor
    @Insert
    void insertNote(Note note);

    // update
    @Update
    void updateNote(Note note);

    // delete
    @Delete
    void deleteNote(Note note);
}
