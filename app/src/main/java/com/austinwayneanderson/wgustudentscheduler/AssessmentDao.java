package com.austinwayneanderson.wgustudentscheduler;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Query("DELETE FROM assessments")
    void deleteAll();

    @Query("SELECT * FROM assessments ORDER BY title ASC")
    LiveData<List<Assessment>> getAlphabetizedAssessments();
}
