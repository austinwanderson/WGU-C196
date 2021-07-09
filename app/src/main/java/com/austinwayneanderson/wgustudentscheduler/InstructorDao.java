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
public interface InstructorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("DELETE FROM instructors")
    void deleteAll();

    @Query("SELECT * FROM instructors WHERE course_id = :course_id ORDER BY name ASC")
    LiveData<List<Instructor>> getCourseInstructors(int course_id);

    @Query("SELECT * FROM instructors ORDER BY name ASC")
    LiveData<List<Instructor>> getAlphabetizedInstructors();
}