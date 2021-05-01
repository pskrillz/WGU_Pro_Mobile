package com.pi.wgu_pro.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tableCourse",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "termId",
                childColumns = "termIdFK",
                onDelete = CASCADE
        )
)
public class Course {
    @PrimaryKey(autoGenerate = true) private int courseIdPK;
    @ColumnInfo() private int termIdFK;
    @ColumnInfo() private String courseName;
    @ColumnInfo() private Date courseStart;
    @ColumnInfo() private Date courseEnd;
    @ColumnInfo() private String courseStatus;
    @ColumnInfo() private boolean courseAlert;

    public Course(int courseIdPK, int termIdFK, String courseName, Date courseStart, Date courseEnd, String courseStatus, boolean courseAlert) {
        this.courseIdPK = courseIdPK;
        this.termIdFK = termIdFK;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseAlert = courseAlert;
    }

    public Course(int termIdFK, String courseName, Date courseStart, Date courseEnd, String courseStatus, boolean courseAlert) {
        this.termIdFK = termIdFK;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseAlert = courseAlert;
    }

    public Course(){

    }

    // getters
    public int getCourseIdPK() {
        return courseIdPK;
    }

    public int getTermIdFK() {
        return termIdFK;
    }

    public String getCourseName() {
        return courseName;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public boolean isCourseAlert() {
        return courseAlert;
    }

    // setters

    public void setTermIdFK(int termIdFK) {
        this.termIdFK = termIdFK;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setCourseIdPK(int courseIdPK) {
        this.courseIdPK = courseIdPK;
    }

    public void setCourseAlert(boolean courseAlert) {
        this.courseAlert = courseAlert;
    }
}
