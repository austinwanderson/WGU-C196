package com.austinwayneanderson.wgustudentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CoursesActivity extends AppCompatActivity {

    private CoursesViewModel mCoursesViewModel;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private Bundle extras;

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
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.course_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}