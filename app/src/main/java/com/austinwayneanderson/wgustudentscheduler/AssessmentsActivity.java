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
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AssessmentsActivity extends AppCompatActivity {

    private AssessmentsViewModel mAssessmentsViewModel;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;

    final Calendar calendar = Calendar.getInstance();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        RecyclerView recyclerView = findViewById(R.id.assessmentList);
        final AssessmentListAdapter adapter = new AssessmentListAdapter(new AssessmentListAdapter.AssessmentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        mAssessmentsViewModel.getAllAssessments().observe(this, assessments -> {
            adapter.submitList(assessments);
        });

        FloatingActionButton fab = findViewById(R.id.addAssessmentFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentsActivity.this, NewAssessmentActivity.class);
            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment assessment = new Assessment(data.getStringExtra(NewAssessmentActivity.REPLY_TITLE),
                    data.getStringExtra(NewAssessmentActivity.REPLY_START),
                    data.getStringExtra(NewAssessmentActivity.REPLY_END),
                    data.getStringExtra(NewAssessmentActivity.REPLY_TYPE),
                    data.getIntExtra(NewAssessmentActivity.REPLY_COURSE_ID,0));
            mAssessmentsViewModel.insert(assessment);
            String myFormat = "dd/MM/yy HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            try {
                long sFuture = sdf.parse(assessment.getStartDate() + " 00:00:00").getTime();
                long sNow = calendar.getTimeInMillis();
                long sDelay = sFuture - sNow;
                System.out.println("start delay: " + sDelay);
                scheduleNotification(getNotification(assessment.getTitle() + " is starting today!", "Assessment " + assessment.getTitle() + " is starting today."), sDelay);

                long fFuture = sdf.parse(assessment.getEndDate() + " 00:00:00").getTime();
                long fNow = calendar.getTimeInMillis();
                long fDelay = fFuture - fNow;
                System.out.println("end delay: " + fDelay);
                scheduleNotification(getNotification(assessment.getTitle() + " is ending today!", assessment.getTitle() + " is ending today!"), fDelay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.assessment_empty_not_saved,
                    Toast.LENGTH_LONG).show();
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