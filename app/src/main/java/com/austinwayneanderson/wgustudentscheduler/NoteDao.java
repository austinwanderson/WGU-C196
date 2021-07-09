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
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes WHERE id = :note_id")
    void deleteNoteById(int note_id);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes ORDER BY title ASC")
    LiveData<List<Note>> getAlphabetizedNotes();

    @Query("SELECT * FROM notes WHERE course_id = :course_id ORDER BY title ASC")
    LiveData<List<Note>> getCourseNotes(int course_id);

    @Query("SELECT * FROM notes WHERE id = :note_id")
    Note getNoteById(String note_id);
}
