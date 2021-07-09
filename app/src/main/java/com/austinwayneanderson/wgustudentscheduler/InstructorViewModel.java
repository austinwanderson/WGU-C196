package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private InstructorRepository mInstructorRepository;
    private LiveData<List<Instructor>> mAllInstructors;
    private LiveData<List<Instructor>> mAllCourseInstructors;
    private int mCourseId = 0;

    public InstructorViewModel(Application application) {
        super(application);
        mInstructorRepository = new InstructorRepository(application);
        mAllInstructors = mInstructorRepository.getAllInstructors();
        mAllCourseInstructors = mInstructorRepository.getAllCourseInstructors(mCourseId);
    }

    LiveData<List<Instructor>> getAllInstructors() {
        return mAllInstructors;
    }
    LiveData<List<Instructor>> getAllCourseInstructors(int course_id) {
        this.setCourseId(course_id);
        System.out.println("this.mCourseId: " + this.mCourseId);
        this.mAllCourseInstructors = mInstructorRepository.getAllCourseInstructors(this.mCourseId);
        return this.mAllCourseInstructors;
    }

    void setCourseId(int course_id) {
        this.mCourseId = course_id;
    }

    void insert(Instructor a) {
        mInstructorRepository.insert(a);
    }
    void update(Instructor a, int id) { mInstructorRepository.update(a, id); }
    void delete(Instructor a) { mInstructorRepository.delete(a); }
}
