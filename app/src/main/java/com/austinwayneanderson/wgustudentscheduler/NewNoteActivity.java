package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewNoteActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditContentsView;
    private NoteViewModel mNotesViewModel;
    public static final String REPLY_TITLE = "com.example.android.courselistsql.TITLE";
    public static final String REPLY_CONTENTS = "com.example.android.courselistsql.CONTENTS";
    public static final String REPLY_COURSE_ID = "com.example.android.courselistsql.COURSE_ID";
    private String edited_note_id = "";
    private int edited_note_course_id;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mEditTitleView = findViewById(R.id.edit_note_title);
        mEditContentsView = findViewById(R.id.edit_note_contents);
        mNotesViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        extras = getIntent().getExtras();
        System.out.println("Note Course ID: " + extras.getString("COURSE_ID"));
        if (extras.getString("EDIT_NOTE_ID") != null) {
            edited_note_id = extras.getString("EDIT_NOTE_ID");
            SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
            Note note = db.noteDao().getNoteById(edited_note_id);
            edited_note_course_id = note.getCourseId();
            mEditTitleView.setText(note.getTitle());
            mEditContentsView.setText(note.getContents());
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            if (edited_note_id.equals("")) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTitleView.getText()) ||
                        TextUtils.isEmpty(mEditContentsView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String title = mEditTitleView.getText().toString();
                    String contents = mEditContentsView.getText().toString();
                    int course_id = Integer.parseInt(extras.getString("COURSE_ID"));
                    replyIntent.putExtra(REPLY_TITLE, title);
                    replyIntent.putExtra(REPLY_CONTENTS, contents);
                    replyIntent.putExtra(REPLY_COURSE_ID, course_id);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            } else {
                String title = mEditTitleView.getText().toString();
                String contents = mEditContentsView.getText().toString();
                int course_id = edited_note_course_id;
                Note note = new Note(title, contents, course_id);
                note.setId(Integer.parseInt(edited_note_id));
                mNotesViewModel.update(note, note.getId());
                Intent intent = new Intent(this, CourseNotesActivity.class);
                intent.putExtra("edited_note_id", edited_note_id);
                intent.putExtra("COURSE_ID", String.valueOf(edited_note_course_id));
                startActivity(intent);
            }
        });
    }
}