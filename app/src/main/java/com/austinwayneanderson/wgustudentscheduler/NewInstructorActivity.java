package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewInstructorActivity extends AppCompatActivity {

    private EditText mEditNameView;
    private EditText mEditPhoneView;
    private EditText mEditEmailView;
    public static final String REPLY_NAME = "com.example.android.courselistsql.NAME";
    public static final String REPLY_PHONE = "com.example.android.courselistsql.PHONE";
    public static final String REPLY_EMAIL = "com.example.android.courselistsql.EMAIL";
    public static final String REPLY_COURSE_ID = "com.example.android.courselistsql.COURSE_ID";
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instructor);
        mEditNameView = findViewById(R.id.edit_instructor_name);
        mEditPhoneView = findViewById(R.id.edit_instructor_phone);
        mEditEmailView = findViewById(R.id.edit_instructor_email);
        extras = getIntent().getExtras();
        System.out.println("Instructor Course ID: " + extras.getString("COURSE_ID"));

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditNameView.getText()) ||
                    TextUtils.isEmpty(mEditPhoneView.getText()) ||
                    TextUtils.isEmpty(mEditEmailView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = mEditNameView.getText().toString();
                String phone = mEditPhoneView.getText().toString();
                String email = mEditEmailView.getText().toString();
                int course_id = Integer.parseInt(extras.getString("COURSE_ID"));
                replyIntent.putExtra(REPLY_NAME, name);
                replyIntent.putExtra(REPLY_PHONE, phone);
                replyIntent.putExtra(REPLY_EMAIL, email);
                replyIntent.putExtra(REPLY_COURSE_ID, course_id);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}