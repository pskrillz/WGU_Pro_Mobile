package com.pi.wgu_pro.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "Note Table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseIdPK",
                childColumns = "courseIdFK",
                onDelete = CASCADE
        )
)
public class Note {
    @PrimaryKey(autoGenerate = true) private int noteId;
    @ColumnInfo() private int courseIdFK;
    @ColumnInfo() private String noteTitle;
    @ColumnInfo() private String noteContent;

    public int getCourseIdFK() {
        return courseIdFK;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setCourseIdFK(int courseIdFK) {
        this.courseIdFK = courseIdFK;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
