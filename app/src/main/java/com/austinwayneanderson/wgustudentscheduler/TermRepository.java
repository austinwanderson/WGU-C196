package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TermRepository {

    private TermDao mTermDao;
    private LiveData<List<Term>> mAllTerms;

    TermRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mAllTerms = mTermDao.getAlphabetizedTerms();
    }

    LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }

    void insert(Term term) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insert(term);
        });
    }

    void update(Term a, int id) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            a.setId(id);
            mTermDao.update(a);
        });
    }

    void delete(Term a) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.delete(a);
        });
    }
}
