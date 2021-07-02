package com.austinwayneanderson.wgustudentscheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InstructorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Instructor instructor);

    @Query("DELETE FROM instructors")
    void deleteAll();

    @Query("SELECT * FROM instructors ORDER BY name ASC")
    List<Instructor> getAlphabetizedInstructors();
}