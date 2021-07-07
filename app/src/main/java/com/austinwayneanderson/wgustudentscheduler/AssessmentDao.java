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
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessments WHERE id = :assessment_id")
    void deleteAssessmentById(int assessment_id);

    @Query("DELETE FROM assessments")
    void deleteAll();

    @Query("SELECT * FROM assessments ORDER BY title ASC")
    LiveData<List<Assessment>> getAlphabetizedAssessments();

    @Query("SELECT * FROM assessments WHERE id = :assessment_id")
    Assessment getAssessmentById(String assessment_id);
}
