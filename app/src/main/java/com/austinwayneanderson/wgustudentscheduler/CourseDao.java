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
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM courses WHERE id = :course_id")
    void deleteCourseById(int course_id);

    @Query("DELETE FROM courses")
    void deleteAll();

    @Query("SELECT * FROM courses ORDER BY title ASC")
    LiveData<List<Course>> getAlphabetizedCourses();

    @Query("SELECT * FROM courses WHERE term_id = :term_id ORDER BY title ASC")
    LiveData<List<Course>> getCoursesInTerm(String term_id);

    @Query("SELECT COUNT(*) FROM courses WHERE term_id = :term_id")
    int getNumberOfCoursesInTerm(String term_id);

    @Query("SELECT * FROM courses WHERE id = :course_id")
    Course getCourseById(String course_id);
}
