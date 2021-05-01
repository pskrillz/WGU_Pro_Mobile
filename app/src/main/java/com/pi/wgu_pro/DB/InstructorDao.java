package com.pi.wgu_pro.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pi.wgu_pro.Entities.Instructor;

import java.util.List;

@Dao
public interface InstructorDao {

    // get all instructors
    @Query("select * from tableInstructor")
    List<Instructor> getAllInstructors();

    // get a specific course's list of instructors
    @Query("select * from tableInstructor where CourseIdFK = :courseId")
    List<Instructor> getCourseInstructors(int courseId);

    //get specific instructor
    @Query("select * from tableInstructor where instructorPK = :instructorId")
    Instructor getSpecInstructor(int instructorId);

    // add instructor
    @Insert
    void insertInstructor(Instructor instructor);

    // update
    @Update
    void updateInstructor(Instructor instructor);

    // delete
    @Delete
    void deleteInstructor(Instructor instructor);
}
