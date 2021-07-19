package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseInstructorsActivity extends AppCompatActivity {

    private InstructorViewModel mInstructorsViewModel;
    public static final int NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_instructors);

        RecyclerView recyclerView = findViewById(R.id.instructorsList);
        final InstructorListAdapter adapter = new InstructorListAdapter(new InstructorListAdapter.InstructorDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mInstructorsViewModel = new ViewModelProvider(this).get(InstructorViewModel.class);

        Bundle extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Course course = db.courseDao().getCourseById(extras.getString("COURSE_ID"));

        if (extras.getString("edited_instructor_id") != null) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.instructor_updated,
                    Toast.LENGTH_LONG).show();
        }

        mInstructorsViewModel.getAllCourseInstructors(course.getId()).observe(this, instructors -> {
            adapter.submitList(instructors);
        });

        FloatingActionButton fab = findViewById(R.id.addInstructorFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CourseInstructorsActivity.this, NewInstructorActivity.class);
            intent.putExtra("COURSE_ID", extras.getString("COURSE_ID"));
            startActivityForResult(intent, NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Instructor instructor = new Instructor(data.getStringExtra(NewInstructorActivity.REPLY_NAME),
                    data.getStringExtra(NewInstructorActivity.REPLY_PHONE),
                    data.getStringExtra(NewInstructorActivity.REPLY_EMAIL),
                    data.getIntExtra(NewInstructorActivity.REPLY_COURSE_ID,-1));
            mInstructorsViewModel.insert(instructor);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.instructor_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
