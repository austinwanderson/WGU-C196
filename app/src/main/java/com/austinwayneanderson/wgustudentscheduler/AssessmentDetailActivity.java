package com.austinwayneanderson.wgustudentscheduler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class AssessmentDetailActivity extends AppCompatActivity {

    private static final int EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private Button editAssessmentButton;
    private TextView assessmentTitle;
    private TextView assessmentStart;
    private TextView assessmentEnd;
    private TextView assessmentCourse;
    private TextView assessmentId;
    private TextView assessmentType;
    private AssessmentsViewModel mAssessmentsViewModel;
    private Bundle extras;

    final Calendar calendar = Calendar.getInstance();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_assessment_item: {
                deleteAssessment();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        ActionBar actionBar = getSupportActionBar();

        editAssessmentButton = findViewById(R.id.editAssessmentButton);
        assessmentTitle = findViewById(R.id.assessmentDetailTitle);
        assessmentStart = findViewById(R.id.assessmentDetailStart);
        assessmentEnd = findViewById(R.id.assessmentDetailEnd);
        assessmentCourse = findViewById(R.id.assessmentDetailCourse);
        assessmentId = findViewById(R.id.assessmentDetailId);
        assessmentType = findViewById(R.id.assessmentDetailType);
        mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Assessment assessment = db.assessmentDao().getAssessmentById(extras.getString("ASSESSMENT_ID"));
        updateInterfaceValues(assessment);
        editAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditAssessmentActivity();
            }
        });
    }

    private void updateInterfaceValues(Assessment assessment) {
        assessmentTitle.setText("Title: " + assessment.getTitle());
        assessmentStart.setText("Start Date: " + assessment.getStartDate());
        assessmentEnd.setText("End Date: " + assessment.getEndDate());
        assessmentCourse.setText("Course ID: " + assessment.getCourseId());
        assessmentId.setText("Assessment ID: " + assessment.getId());
        assessmentType.setText("Type: " + assessment.getType());
    }

    private void deleteAssessment() {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Assessment assessment = db.assessmentDao().getAssessmentById(extras.getString("ASSESSMENT_ID"));
        mAssessmentsViewModel.delete(assessment);
        Intent intent = new Intent(this, AssessmentsActivity.class);
        startActivity(intent);
        Toast.makeText(
                getApplicationContext(),
                R.string.assessment_deleted,
                Toast.LENGTH_LONG).show();
    }

    private void openEditAssessmentActivity() {
        Intent intent = new Intent(AssessmentDetailActivity.this, NewAssessmentActivity.class);
        intent.putExtra("EDIT_ASSESSMENT_ID", extras.getString("ASSESSMENT_ID"));
        startActivityForResult(intent, EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment assessment = new Assessment(data.getStringExtra(NewAssessmentActivity.REPLY_TITLE),
                    data.getStringExtra(NewAssessmentActivity.REPLY_START),
                    data.getStringExtra(NewAssessmentActivity.REPLY_END),
                    data.getStringExtra(NewAssessmentActivity.REPLY_TYPE),
                    data.getIntExtra(NewAssessmentActivity.REPLY_COURSE_ID,0));
            mAssessmentsViewModel.update(assessment, data.getIntExtra(NewAssessmentActivity.REPLY_ASSESSMENT_ID, -1));
            assessment.setId(data.getIntExtra(NewAssessmentActivity.REPLY_ASSESSMENT_ID, -1));
            updateInterfaceValues(assessment);
            String myFormat = "dd/MM/yy HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            try {
                long sFuture = sdf.parse(assessment.getStartDate() + " 00:00:00").getTime();
                scheduleNotification(getNotification(assessment.getTitle() + " is starting today!", "Assessment " + assessment.getTitle() + " is starting today."), sFuture, 6);
                long fFuture = sdf.parse(assessment.getEndDate() + " 00:00:00").getTime();
                scheduleNotification(getNotification(assessment.getTitle() + " is ending today!", assessment.getTitle() + " is ending today!"), fFuture, 7);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(
                    getApplicationContext(),
                    R.string.assessment_updated,
                    Toast.LENGTH_LONG).show();
        } else {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.assessment_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
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
