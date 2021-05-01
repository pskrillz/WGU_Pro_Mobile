package com.pi.wgu_pro.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tableInstructor",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseIdPK",
                childColumns = "courseIdFK",
                onDelete = CASCADE
        )
)
public class CourseInstructor {
    @PrimaryKey(autoGenerate = true) private int instructorPK;
    @ColumnInfo() private int courseIdFK;
    @ColumnInfo() private String name;
    @ColumnInfo() private String phone;
    @ColumnInfo() private String email;

    public int getInstructorPK() {
        return instructorPK;
    }

    public int getCourseIdFK() {
        return courseIdFK;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setInstructorPK(int instructorPK) {
        this.instructorPK = instructorPK;
    }

    public void setCourseIdFK(int courseIdFK) {
        this.courseIdFK = courseIdFK;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
