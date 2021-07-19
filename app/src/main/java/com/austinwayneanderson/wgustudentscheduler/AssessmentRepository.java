package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AssessmentRepository {

    private AssessmentDao mAssessmentDao;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Assessment>> mAllAssessmentsInCourse;

    AssessmentRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mAssessmentDao = db.assessmentDao();
        mAllAssessments = mAssessmentDao.getAlphabetizedAssessments();
    }

    LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    }

    void insert(Assessment assessment) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
    }

    void update(Assessment a, int id) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            a.setId(id);
            mAssessmentDao.update(a);
        });
    }

    void delete(Assessment a) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.delete(a);
        });
    }

    public LiveData<List<Assessment>> getAssessmentsInCourse(String currentCourse) {
        mAllAssessmentsInCourse = mAssessmentDao.getAssessmentsInCourse(currentCourse);
        return mAllAssessmentsInCourse;
    }
}
