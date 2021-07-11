package com.austinwayneanderson.wgustudentscheduler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseDetailActivity extends AppCompatActivity {

    private static final int EDITED_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private Button editCourseButton;
    private Button viewNotesButton;
    private Button viewInstructorsButton;
    private TextView courseTitle;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseTermId;
    private TextView courseStatus;
    private TextView courseId;
    private CoursesViewModel mCoursesViewModel;
    private Bundle extras;

    final Calendar calendar = Calendar.getInstance();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_course_item: {
                deleteCourse();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        ActionBar actionBar = getSupportActionBar();

        editCourseButton = findViewById(R.id.editCourseButton);
        courseTitle = findViewById(R.id.courseDetailTitle);
        System.out.println("CourseTitle " + courseTitle);
        courseStart = findViewById(R.id.courseDetailStart);
        courseEnd = findViewById(R.id.courseDetailEnd);
        courseTermId = findViewById(R.id.courseDetailTermId);
        courseStatus = findViewById(R.id.courseDetailStatus);
        courseId = findViewById(R.id.courseDetailId);
        viewNotesButton = findViewById(R.id.viewCourseNotesButton);
        viewInstructorsButton = findViewById(R.id.viewCourseInstructorsButton);
        mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Course course = db.courseDao().getCourseById(extras.getString("COURSE_ID"));
        updateInterfaceValues(course);
        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditCourseActivity();
            }
        });

        viewNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCourseNotesActivity();
            }
        });

        viewInstructorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCourseInstructorsActivity();
            }
        });
    }

    private void openCourseNotesActivity() {
        Intent intent = new Intent(CourseDetailActivity.this, CourseNotesActivity.class);
        intent.putExtra("COURSE_ID", extras.getString("COURSE_ID"));
        startActivity(intent);
    }

    private void openCourseInstructorsActivity() {
        Intent intent = new Intent(CourseDetailActivity.this, CourseInstructorsActivity.class);
        intent.putExtra("COURSE_ID", extras.getString("COURSE_ID"));
        startActivity(intent);
    }

    private void updateInterfaceValues(Course course) {
        courseTitle.setText("Title: " + course.getTitle());
        courseStart.setText("Start Date: " + course.getStartDate());
        courseEnd.setText("End Date: " + course.getEndDate());
        courseTermId.setText("Term ID: " + course.getTermId());
        courseStatus.setText("Status: " + course.getStatus());
        courseId.setText("Course ID: " + course.getId());
    }

    private void deleteCourse() {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Course course = db.courseDao().getCourseById(extras.getString("COURSE_ID"));
        mCoursesViewModel.delete(course);
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
        Toast.makeText(
                getApplicationContext(),
                R.string.course_deleted,
                Toast.LENGTH_LONG).show();
    }

    private void openEditCourseActivity() {
        Intent intent = new Intent(CourseDetailActivity.this, NewCourseActivity.class);
        intent.putExtra("EDIT_COURSE_ID", extras.getString("COURSE_ID"));
        startActivityForResult(intent, EDITED_COURSE_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITED_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = new Course(data.getStringExtra(NewCourseActivity.REPLY_TITLE),
                    data.getStringExtra(NewCourseActivity.REPLY_START),
                    data.getStringExtra(NewCourseActivity.REPLY_END),
                    data.getStringExtra(NewCourseActivity.REPLY_STATUS),
                    data.getIntExtra(NewCourseActivity.REPLY_TERM_ID,-1));
            mCoursesViewModel.update(course, data.getIntExtra(NewCourseActivity.REPLY_COURSE_ID, -1));
            course.setId(data.getIntExtra(NewCourseActivity.REPLY_COURSE_ID, -1));
            updateInterfaceValues(course);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.course_updated,
                    Toast.LENGTH_LONG).show();

            String myFormat = "dd/MM/yy HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            try {
                long sFuture = sdf.parse(course.getStartDate() + " 17:38:00").getTime();
                long sNow = calendar.getTimeInMillis();
                long sDelay = sFuture - sNow;
                System.out.println("start delay: " + sDelay);
                scheduleNotification(getNotification(course.getTitle() + " is starting today!", "Course " + course.getTitle() + " is starting today."), sDelay);

                long fFuture = sdf.parse(course.getEndDate() + " 00:00:00").getTime();
                long fNow = calendar.getTimeInMillis();
                long fDelay = fFuture - fNow;
                System.out.println("start delay: " + fDelay);
                scheduleNotification(getNotification(course.getTitle() + " is ending today!", course.getTitle() + " is ending today!"), fDelay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.course_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
    }

    private Notification getNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }
}
