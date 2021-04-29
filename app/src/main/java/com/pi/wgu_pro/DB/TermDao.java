package com.pi.wgu_pro.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pi.wgu_pro.Entities.Term;

import java.util.List;

@Dao
public interface TermDao {

    // get all
    @Query("select * from TermTable")
    List<Term> getAllTerms();

    //get specific
    @Query("select * from TermTable where termId = :termId")
    Term getSpecTerm(int termId);

    // add term/save
    @Insert
    void insertTerm(Term term);

    // update
    @Update
    void updateTerm(Term term);

    // delete
    @Delete
    void deleteTerm(Term term);


}
