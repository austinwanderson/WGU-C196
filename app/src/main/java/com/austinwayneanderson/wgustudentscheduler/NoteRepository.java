package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;
    private LiveData<List<Note>> mAllCourseNotes;

    NoteRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAlphabetizedNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
    LiveData<List<Note>> getAllCourseNotes(int course_id) {
        mAllCourseNotes = mNoteDao.getCourseNotes(course_id);
        return mAllCourseNotes;
    }

    void insert(Note note) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }

    void update(Note a, int id) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            a.setId(id);
            mNoteDao.update(a);
        });
    }

    void delete(Note a) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.delete(a);
        });
    }
}
