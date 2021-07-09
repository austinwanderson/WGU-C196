package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditContentsView;
    public static final String REPLY_TITLE = "com.example.android.courselistsql.TITLE";
    public static final String REPLY_CONTENTS = "com.example.android.courselistsql.CONTENTS";
    public static final String REPLY_COURSE_ID = "com.example.android.courselistsql.COURSE_ID";
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mEditTitleView = findViewById(R.id.edit_note_title);
        mEditContentsView = findViewById(R.id.edit_note_contents);
        extras = getIntent().getExtras();
        System.out.println("Note Course ID: " + extras.getString("COURSE_ID"));

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
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
        });
    }
}