package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CourseRepository {

    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mAllCoursesInTerm;

    CourseRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mCourseDao = db.courseDao();
        mAllCourses = mCourseDao.getAlphabetizedCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }
    LiveData<List<Course>> getCoursesInTerm(String term_id) {
        mAllCoursesInTerm = mCourseDao.getCoursesInTerm(term_id);
        return mAllCoursesInTerm;
    }

    void insert(Course course) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
    }

    void update(Course a, int id) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            a.setId(id);
            mCourseDao.update(a);
        });
    }

    void delete(Course a) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.delete(a);
        });
    }
}
