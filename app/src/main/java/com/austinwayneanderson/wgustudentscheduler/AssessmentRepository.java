package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AssessmentRepository {

    private AssessmentDao mAssessmentDao;
    private LiveData<List<Assessment>> mAllAssessments;

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
}
