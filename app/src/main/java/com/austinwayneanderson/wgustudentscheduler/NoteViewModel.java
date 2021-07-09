package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> mAllNotes;
    private LiveData<List<Note>> mAllCourseNotes;
    private int mCourseId = 0;

    public NoteViewModel(Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
        mAllNotes = mNoteRepository.getAllNotes();
        mAllCourseNotes = mNoteRepository.getAllCourseNotes(mCourseId);
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
    LiveData<List<Note>> getAllCourseNotes(int course_id) {
        this.setCourseId(course_id);
        System.out.println("this.mCourseId: " + this.mCourseId);
        this.mAllCourseNotes = mNoteRepository.getAllCourseNotes(this.mCourseId);
        return this.mAllCourseNotes;
    }

    void setCourseId(int course_id) {
        this.mCourseId = course_id;
    }

    void insert(Note a) {
        mNoteRepository.insert(a);
    }
    void update(Note a, int id) { mNoteRepository.update(a, id); }
    void delete(Note a) { mNoteRepository.delete(a); }
}
