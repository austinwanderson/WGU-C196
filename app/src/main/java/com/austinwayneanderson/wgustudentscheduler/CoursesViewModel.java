package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CoursesViewModel extends AndroidViewModel {
    private CourseRepository mCourseRepository;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mCoursesInTerm;
    private String mCurrentTerm;

    public CoursesViewModel(Application application) {
        super(application);
        mCourseRepository = new CourseRepository(application);
        mAllCourses = mCourseRepository.getAllCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }
    LiveData<List<Course>> getCoursesInTerm(String term_id) {
        this.setCurrentTerm(term_id);
        this.mCoursesInTerm = mCourseRepository.getCoursesInTerm(this.mCurrentTerm);
        return this.mCoursesInTerm;
    }

    private void setCurrentTerm(String term_id) {
        this.mCurrentTerm = term_id;
    }



    void insert(Course a) {
        mCourseRepository.insert(a);
    }
    void update(Course a, int id) { mCourseRepository.update(a, id); }
    void delete(Course a) { mCourseRepository.delete(a); }
}
