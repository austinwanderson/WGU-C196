package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class InstructorRepository {

    private InstructorDao mInstructorDao;
    private LiveData<List<Instructor>> mAllInstructors;
    private LiveData<List<Instructor>> mAllCourseInstructors;

    InstructorRepository(Application application) {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(application);
        mInstructorDao = db.instructorDao();
        mAllInstructors = mInstructorDao.getAlphabetizedInstructors();
    }

    LiveData<List<Instructor>> getAllInstructors() {
        return mAllInstructors;
    }
    LiveData<List<Instructor>> getAllCourseInstructors(int course_id) {
        mAllCourseInstructors = mInstructorDao.getCourseInstructors(course_id);
        return mAllCourseInstructors;
    }

    void insert(Instructor instructor) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mInstructorDao.insert(instructor);
        });
    }

    void update(Instructor a, int id) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            a.setId(id);
            mInstructorDao.update(a);
        });
    }

    void delete(Instructor a) {
        SchedulerRoomDatabase.databaseWriteExecutor.execute(() -> {
            mInstructorDao.delete(a);
        });
    }
}
