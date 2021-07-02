package com.austinwayneanderson.wgustudentscheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Query("DELETE FROM terms")
    void deleteAll();

    @Query("SELECT * FROM terms ORDER BY title ASC")
    List<Term> getAlphabetizedInstructors();
}