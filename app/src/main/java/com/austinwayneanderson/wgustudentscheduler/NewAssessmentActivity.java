package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewAssessmentActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditStartDateView;
    private EditText mEditEndDateView;
    private EditText mEditCourseIdView;
    private RadioGroup mEditAssessmentType;
    private RadioButton performanceType;
    private RadioButton objectiveType;
    private boolean isEditingAssessment = false;
    private Assessment editingAssessment;
    public static final String REPLY_TITLE = "com.example.android.assessmentlistsql.TITLE";
    public static final String REPLY_START = "com.example.android.assessmentlistsql.START_DATE";
    public static final String REPLY_END = "com.example.android.assessmentlistsql.END_DATE";
    public static final String REPLY_TYPE = "com.example.android.assessmentlistsql.TYPE";
    public static final String REPLY_COURSE_ID = "com.example.android.assessmentlistsql.COURSE_ID";
    public static final String REPLY_ASSESSMENT_ID = "com.example.android.assessmentlistsql.ID";
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);
        mEditTitleView = findViewById(R.id.edit_assessment_title);
        mEditStartDateView = findViewById(R.id.edit_assessment_start_date);
        mEditEndDateView = findViewById(R.id.edit_assessment_end_date);
        mEditCourseIdView = findViewById(R.id.edit_assessment_course_id);
        mEditAssessmentType = findViewById(R.id.edit_assessment_type);
        performanceType = findViewById(R.id.performanceType);
        objectiveType = findViewById(R.id.objectiveType);
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());

        if (getIntent().getExtras() != null) {
            extras = getIntent().getExtras();
            if (extras.containsKey("EDIT_ASSESSMENT_ID")) {
                isEditingAssessment = true;
                editingAssessment = db.assessmentDao().getAssessmentById(extras.getString("EDIT_ASSESSMENT_ID"));
                mEditTitleView.setText(editingAssessment.getTitle());
                mEditStartDateView.setText(editingAssessment.getStartDate());
                mEditEndDateView.setText(editingAssessment.getEndDate());
                mEditCourseIdView.setText(String.valueOf(editingAssessment.getCourseId()));
                System.out.println("Editing Assessment Type: " + editingAssessment.getType());
                if (editingAssessment.getType().equals("Performance")) {
                    mEditAssessmentType.check(R.id.performanceType);
                } else {
                    mEditAssessmentType.check(R.id.objectiveType);
                }
            }
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitleView.getText()) ||
                    TextUtils.isEmpty(mEditEndDateView.getText()) ||
                    TextUtils.isEmpty(mEditCourseIdView.getText()) ||
                    TextUtils.isEmpty(mEditStartDateView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = mEditTitleView.getText().toString();
                String startDate = mEditStartDateView.getText().toString();
                String endDate = mEditEndDateView.getText().toString();
                int course_id = Integer.parseInt(mEditCourseIdView.getText().toString());
                String type = (mEditAssessmentType.getCheckedRadioButtonId() == performanceType.getId()) ? "Performance" : "Objective";
                replyIntent.putExtra(REPLY_TITLE, title);
                replyIntent.putExtra(REPLY_START, startDate);
                replyIntent.putExtra(REPLY_END, endDate);
                replyIntent.putExtra(REPLY_TYPE, type);
                replyIntent.putExtra(REPLY_COURSE_ID, course_id);
                if (isEditingAssessment) {
                    replyIntent.putExtra(REPLY_ASSESSMENT_ID, editingAssessment.getId());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}