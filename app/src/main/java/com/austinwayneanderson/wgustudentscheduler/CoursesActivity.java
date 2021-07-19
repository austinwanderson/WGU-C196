package com.austinwayneanderson.wgustudentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CoursesActivity extends AppCompatActivity {

    private CoursesViewModel mCoursesViewModel;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private Bundle extras;
    final Calendar calendar = Calendar.getInstance();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        if (getIntent().getExtras() != null) {
            extras = getIntent().getExtras();
            System.out.println(extras.getString("TERM_ID"));
            if (extras.getString("TERM_ID") != null) {
                showTermCourses(extras.getString("TERM_ID"));
            } else {
                showAllCourses();
            }
        } else {
            showAllCourses();
        }
    }

    private void showTermCourses(String term_id) {
        RecyclerView recyclerView = findViewById(R.id.coursesList);
        final CourseListAdapter adapter = new CourseListAdapter(new CourseListAdapter.CourseDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        mCoursesViewModel.getCoursesInTerm(term_id).observe(this, courses -> {
            adapter.submitList(courses);
        });

        FloatingActionButton fab = findViewById(R.id.addCourseFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CoursesActivity.this, NewCourseActivity.class);
            intent.putExtra("TERM_ID", term_id);
            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
        });
    }

    private void showAllCourses() {
        RecyclerView recyclerView = findViewById(R.id.coursesList);
        final CourseListAdapter adapter = new CourseListAdapter(new CourseListAdapter.CourseDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        mCoursesViewModel.getAllCourses().observe(this, courses -> {
            adapter.submitList(courses);
        });

        FloatingActionButton fab = findViewById(R.id.addCourseFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CoursesActivity.this, NewCourseActivity.class);
            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = new Course(data.getStringExtra(NewCourseActivity.REPLY_TITLE),
                    data.getStringExtra(NewCourseActivity.REPLY_START),
                    data.getStringExtra(NewCourseActivity.REPLY_END),
                    data.getStringExtra(NewCourseActivity.REPLY_STATUS),
                    data.getIntExtra(NewCourseActivity.REPLY_TERM_ID,-1));
            mCoursesViewModel.insert(course);
            String myFormat = "dd/MM/yy HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            try {
                long sFuture = sdf.parse(course.getStartDate() + " 00:00:00").getTime();
                scheduleNotification(getNotification(course.getTitle() + " is starting today!", "Course " + course.getTitle() + " is starting today."), sFuture, 2);
                long fFuture = sdf.parse(course.getEndDate() + " 00:00:00").getTime();
                scheduleNotification(getNotification(course.getTitle() + " is ending today!", course.getTitle() + " is ending today!"), fFuture, 3);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.course_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void scheduleNotification(Notification notification, long delay, int broadcast_id) {
        Intent notificationIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www." + delay + ".com"), this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, broadcast_id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        System.out.println("delay: " + delay);
        System.out.println("broadcast ID: " + broadcast_id);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
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