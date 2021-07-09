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

public class CourseNotesActivity extends AppCompatActivity {

    private NoteViewModel mNotesViewModel;
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        RecyclerView recyclerView = findViewById(R.id.notesList);
        final NoteListAdapter adapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNotesViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Bundle extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Course course = db.courseDao().getCourseById(extras.getString("COURSE_ID"));

        System.out.println("Course ID for notes: " + course.getId());

        mNotesViewModel.getAllCourseNotes(course.getId()).observe(this, notes -> {
            adapter.submitList(notes);
        });

        FloatingActionButton fab = findViewById(R.id.addNoteFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CourseNotesActivity.this, NewNoteActivity.class);
            intent.putExtra("COURSE_ID", extras.getString("COURSE_ID"));
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NewNoteActivity.REPLY_TITLE),
                    data.getStringExtra(NewNoteActivity.REPLY_CONTENTS),
                    data.getIntExtra(NewNoteActivity.REPLY_COURSE_ID,-1));
            mNotesViewModel.insert(note);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.note_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
