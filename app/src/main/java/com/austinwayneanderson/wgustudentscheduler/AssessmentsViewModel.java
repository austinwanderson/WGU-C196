package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentsViewModel extends AndroidViewModel {
    private AssessmentRepository mAssessmentRepository;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Assessment>> mAssessmentsInCourse;
    private String currentCourse;

    public AssessmentsViewModel(Application application) {
        super(application);
        mAssessmentRepository = new AssessmentRepository(application);
        mAllAssessments = mAssessmentRepository.getAllAssessments();
    }

    LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    }

    void insert(Assessment a) {
        mAssessmentRepository.insert(a);
    }
    void update(Assessment a, int id) { mAssessmentRepository.update(a, id); }
    void delete(Assessment a) { mAssessmentRepository.delete(a); }

    public LiveData<List<Assessment>> getCourseAssessments(String course_id) {
//        this.setCurrentTerm(term_id);
//        this.mCoursesInTerm = mCourseRepository.getCoursesInTerm(this.mCurrentTerm);
//        return this.mCoursesInTerm;
        this.setCurrentCourse(course_id);
        this.mAssessmentsInCourse = mAssessmentRepository.getAssessmentsInCourse(this.currentCourse);
        return mAssessmentsInCourse;
    }

    private void setCurrentCourse(String course_id) { this.currentCourse = course_id; }
}
