package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

public class DataRepository {

    private AssessmentDao aDao;
    private CourseDao cDao;
    private InstructorDao iDao;
    private TermDao tDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DataRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        aDao = db.assessmentDao();
        cDao = db.courseDao();
        iDao = db.instructorDao();
        tDao = db.termDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    /*LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }*/

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    /*void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    */
}
