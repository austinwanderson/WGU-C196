package com.austinwayneanderson.wgustudentscheduler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Assessment.class, Course.class, Instructor.class, Term.class}, version = 1, exportSchema = false)
public abstract class SchedulerRoomDatabase extends RoomDatabase {

    public abstract AssessmentDao assessmentDao();
    public abstract InstructorDao instructorDao();
    public abstract CourseDao courseDao();
    public abstract TermDao termDao();

    private static volatile SchedulerRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SchedulerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerRoomDatabase.class, "scheduler_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


