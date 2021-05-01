package com.pi.wgu_pro.DB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pi.wgu_pro.Entities.Assessment;
import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.Entities.CourseInstructor;
import com.pi.wgu_pro.Entities.Note;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.Utils.DateConverter;

@androidx.room.Database(version = 5, exportSchema = false,
        entities = {Assessment.class, Course.class, CourseInstructor.class, Note.class, Term.class})
@TypeConverters({DateConverter.class})
public  abstract class Database extends RoomDatabase {

    private static String name = "wguPro.db";
    private static Database instance;

    public static synchronized Database getInstance(Context ctx){
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),
                    Database.class, name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

     public abstract TermDao termDao();

    public static void destroyInstance(){
        instance = null;
    }

}
