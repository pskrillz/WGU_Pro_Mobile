package com.pi.wgu_pro.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "assessment_table",
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
}
