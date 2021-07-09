package com.austinwayneanderson.wgustudentscheduler;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

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
        } else {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.course_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
