package com.pi.wgu_pro.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pi.wgu_pro.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    // get all assessments
    @Query("select * from tableAssessment")
    List<Assessment> getAllAssessments();

    // get a specific Course's list of assessment
    @Query("select * from tableAssessment where courseIdFK = :courseId")
    List<Assessment> getCourseAssessments(int courseId);

    //get specific
    @Query("select * from tableAssessment where assessmentId = :assessmentId")
    Assessment getSpecAssessment(int assessmentId);

    // add term/save
    @Insert
    void insertAssessment(Assessment assessment);

    // update
    @Update
    void updateAssessment(Assessment assessment);

    // delete
    @Delete
    void deleteAssessment(Assessment assessment);

}
