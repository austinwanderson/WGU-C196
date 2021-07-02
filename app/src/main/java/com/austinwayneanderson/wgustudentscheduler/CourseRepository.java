package com.austinwayneanderson.wgustudentscheduler;
/*
import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CourseRepository {

    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    CourseRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mCourseDao = db.courseDao();
        mAllCourses = mCourseDao.getAlphabetizedCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    void insert(Course course) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
    }
}
*/