package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentsViewModel extends AndroidViewModel {
    private AssessmentRepository mAssessmentRepository;
    private LiveData<List<Assessment>> mAllAssessments;

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
}
