package com.pi.wgu_pro.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tableAssessment",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseIdPK",
                childColumns = "courseIdFK",
                onDelete = CASCADE
        )
)
public class Assessment {
    @PrimaryKey(autoGenerate = true) private int assessmentId;
    @ColumnInfo() private int courseIdFK;
    @ColumnInfo() private String assessmentType;
    @ColumnInfo() private String assessmentTitle;
    @ColumnInfo() private Date assessmentStart;
    @ColumnInfo() private Date assessmentEnd;
    @ColumnInfo() private boolean assessmentAlert;

    @Ignore
    public Assessment(int assessmentId, int courseIdFK, String assessmentType,
                      String assessmentTitle, Date assessmentStart,
                      Date assessmentEnd, boolean assessmentAlert) {
        this.assessmentId = assessmentId;
        this.courseIdFK = courseIdFK;
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentAlert = assessmentAlert;
    }

    public Assessment(int courseIdFK, String assessmentType,
                      String assessmentTitle, Date assessmentStart,
                      Date assessmentEnd, boolean assessmentAlert) {
        this.assessmentId = assessmentId;
        this.courseIdFK = courseIdFK;
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentAlert = assessmentAlert;
    }

    @Ignore
    public Assessment() {
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public int getCourseIdFK() {
        return courseIdFK;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public Date getAssessmentStart() {
        return assessmentStart;
    }

    public Date getAssessmentEnd() {
        return assessmentEnd;
    }

    public boolean isAssessmentAlert() {
        return assessmentAlert;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setCourseIdFK(int courseIdFK) {
        this.courseIdFK = courseIdFK;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentStart(Date assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public void setAssessmentEnd(Date assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public void setAssessmentAlert(boolean assessmentAlert) {
        this.assessmentAlert = assessmentAlert;
    }
}
