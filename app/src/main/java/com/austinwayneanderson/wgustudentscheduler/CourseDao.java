package com.austinwayneanderson.wgustudentscheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Query("DELETE FROM courses")
    void deleteAll();

    @Query("SELECT * FROM courses ORDER BY title ASC")
    List<Course> getAlphabetizedCourses();
}