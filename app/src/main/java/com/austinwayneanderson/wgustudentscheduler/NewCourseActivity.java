package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewCourseActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditStartDateView;
    private EditText mEditEndDateView;
    private EditText mEditTermIdView;
    private RadioGroup mEditStatusView;
    private RadioButton completedStatus;
    private RadioButton droppedStatus;
    private RadioButton planToTakeStatus;
    private RadioButton inProgressStatus;
    private boolean isEditingCourse = false;
    private Course editingCourse;
    public static final String REPLY_TITLE = "com.example.android.courselistsql.TITLE";
    public static final String REPLY_START = "com.example.android.courselistsql.START_DATE";
    public static final String REPLY_END = "com.example.android.courselistsql.END_DATE";
    public static final String REPLY_STATUS = "com.example.android.courselistsql.STATUS";
    public static final String REPLY_TERM_ID = "com.example.android.courselistsql.TERM_ID";
    public static final String REPLY_COURSE_ID = "com.example.android.courselistsql.ID";
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        mEditTitleView = findViewById(R.id.edit_course_title);
        mEditStartDateView = findViewById(R.id.edit_course_start_date);
        mEditEndDateView = findViewById(R.id.edit_course_end_date);
        mEditTermIdView = findViewById(R.id.edit_course_term_id);
        mEditStatusView = findViewById(R.id.edit_course_status);
        completedStatus = findViewById(R.id.completedStatus);
        droppedStatus = findViewById(R.id.droppedStatus);
        planToTakeStatus = findViewById(R.id.planToTakeStatus);
        inProgressStatus = findViewById(R.id.inProgressStatus);

        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());

        if (getIntent().getExtras() != null) {
            extras = getIntent().getExtras();
            if (extras.containsKey("EDIT_COURSE_ID")) {
                isEditingCourse = true;
                editingCourse = db.courseDao().getCourseById(extras.getString("EDIT_COURSE_ID"));
                mEditTitleView.setText(editingCourse.getTitle());
                mEditStartDateView.setText(editingCourse.getStartDate());
                mEditEndDateView.setText(editingCourse.getEndDate());
                mEditTermIdView.setText(String.valueOf(editingCourse.getTermId()));
                if (editingCourse.getStatus().equals("Completed")) {
                    mEditStatusView.check(R.id.completedStatus);
                } else if (editingCourse.getStatus().equals("In Progress")) {
                    mEditStatusView.check(R.id.inProgressStatus);
                } else if (editingCourse.getStatus().equals("Dropped")) {
                    mEditStatusView.check(R.id.droppedStatus);
                } else {
                    mEditStatusView.check(R.id.planToTakeStatus);
                }
            }
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitleView.getText()) ||
                    TextUtils.isEmpty(mEditEndDateView.getText()) ||
                    TextUtils.isEmpty(mEditTermIdView.getText()) ||
                    TextUtils.isEmpty(mEditStartDateView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = mEditTitleView.getText().toString();
                String startDate = mEditStartDateView.getText().toString();
                String endDate = mEditEndDateView.getText().toString();
                int term_id = Integer.parseInt(mEditTermIdView.getText().toString());
                String status = "";
                if (mEditStatusView.getCheckedRadioButtonId() == completedStatus.getId()) {
                    status = "Completed";
                } else if (mEditStatusView.getCheckedRadioButtonId() == inProgressStatus.getId()) {
                    status = "In Progress";
                } else if (mEditStatusView.getCheckedRadioButtonId() == droppedStatus.getId()) {
                    status = "Dropped";
                } else {
                    status = "Plan To Take";
                }
                replyIntent.putExtra(REPLY_TITLE, title);
                replyIntent.putExtra(REPLY_START, startDate);
                replyIntent.putExtra(REPLY_END, endDate);
                replyIntent.putExtra(REPLY_STATUS, status);
                replyIntent.putExtra(REPLY_TERM_ID, term_id);
                if (isEditingCourse) {
                    replyIntent.putExtra(REPLY_COURSE_ID, editingCourse.getId());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}