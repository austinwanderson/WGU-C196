package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewAssessmentActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditStartDateView;
    private EditText mEditEndDateView;
    public static final String REPLY_TITLE = "com.example.android.assessmentlistsql.TITLE";
    public static final String REPLY_START = "com.example.android.assessmentlistsql.START_DATE";
    public static final String REPLY_END = "com.example.android.assessmentlistsql.END_DATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);
        mEditTitleView = findViewById(R.id.edit_assessment_title);
        mEditStartDateView = findViewById(R.id.edit_assessment_start_date);
        mEditEndDateView = findViewById(R.id.edit_assessment_end_date);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitleView.getText()) ||
                    TextUtils.isEmpty(mEditEndDateView.getText()) ||
                    TextUtils.isEmpty(mEditStartDateView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = mEditTitleView.getText().toString();
                String startDate = mEditStartDateView.getText().toString();
                String endDate = mEditEndDateView.getText().toString();
                replyIntent.putExtra(REPLY_TITLE, title);
                replyIntent.putExtra(REPLY_START, startDate);
                replyIntent.putExtra(REPLY_END, endDate);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}