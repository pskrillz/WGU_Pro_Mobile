package com.pi.wgu_pro.DB;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pi.wgu_pro.Entities.Course;

import java.util.List;

public interface CourseDao {

    @Query("select * from tableCourse")
    List<Course> getAllCourses();

    // get a specific term's list of courses
    @Query("select * from tableCourse where termIdFK = :termId")
    List<Course> getTermCourses(int termId);

    //get specific
    @Query("select * from tableCourse where courseIdPK = :courseId")
    Course getSpecCourse(int courseId);

    // add term/save
    @Insert
    void insertCourse(Course course);

    // update
    @Update
    void updateCourse(Course course);

    // delete
    @Delete
    void deleteCourse(Course course);
}
