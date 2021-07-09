package com.austinwayneanderson.wgustudentscheduler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM terms WHERE id = :term_id")
    void deleteTermById(int term_id);

    @Query("DELETE FROM terms")
    void deleteAll();

    @Query("SELECT * FROM terms ORDER BY title ASC")
    LiveData<List<Term>> getAlphabetizedTerms();

    @Query("SELECT * FROM terms WHERE id = :term_id")
    Term getTermById(String term_id);
}