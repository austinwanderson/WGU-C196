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

public class AssessmentDetailActivity extends AppCompatActivity {

    private static final int EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private Button editAssessmentButton;
    private TextView assessmentTitle;
    private TextView assessmentStart;
    private TextView assessmentEnd;
    private TextView assessmentCourse;
    private TextView assessmentId;
    private TextView assessmentType;
    private AssessmentsViewModel mAssessmentsViewModel;
    private Bundle extras;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_assessment_item: {
                deleteAssessment();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        ActionBar actionBar = getSupportActionBar();

        editAssessmentButton = findViewById(R.id.editAssessmentButton);
        assessmentTitle = findViewById(R.id.assessmentDetailTitle);
        assessmentStart = findViewById(R.id.assessmentDetailStart);
        assessmentEnd = findViewById(R.id.assessmentDetailEnd);
        assessmentCourse = findViewById(R.id.assessmentDetailCourse);
        assessmentId = findViewById(R.id.assessmentDetailId);
        assessmentType = findViewById(R.id.assessmentDetailType);
        mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Assessment assessment = db.assessmentDao().getAssessmentById(extras.getString("ASSESSMENT_ID"));
        updateInterfaceValues(assessment);
        editAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditAssessmentActivity();
            }
        });

        /*deleteAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssessment();
            }
        });*/

    }

    private void updateInterfaceValues(Assessment assessment) {
        //System.out.println("UpdateInterfaceValues: " + assessment_id);
        //SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        //Assessment assessment = db.assessmentDao().getAssessmentById(assessment_id);
        //System.out.println(assessment);
        assessmentTitle.setText("Title: " + assessment.getTitle());
        assessmentStart.setText("Start Date: " + assessment.getStartDate());
        assessmentEnd.setText("End Date: " + assessment.getEndDate());
        assessmentCourse.setText("Course ID: " + assessment.getCourseId());
        assessmentId.setText("Assessment ID: " + assessment.getId());
        assessmentType.setText("Type: " + assessment.getType());
    }

    private void deleteAssessment() {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Assessment assessment = db.assessmentDao().getAssessmentById(extras.getString("ASSESSMENT_ID"));
        mAssessmentsViewModel.delete(assessment);
        Intent intent = new Intent(this, AssessmentsActivity.class);
        startActivity(intent);
        Toast.makeText(
                getApplicationContext(),
                R.string.assessment_deleted,
                Toast.LENGTH_LONG).show();
    }

    private void openEditAssessmentActivity() {
        Intent intent = new Intent(AssessmentDetailActivity.this, NewAssessmentActivity.class);
        intent.putExtra("EDIT_ASSESSMENT_ID", extras.getString("ASSESSMENT_ID"));
        startActivityForResult(intent, EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITED_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment assessment = new Assessment(data.getStringExtra(NewAssessmentActivity.REPLY_TITLE),
                    data.getStringExtra(NewAssessmentActivity.REPLY_START),
                    data.getStringExtra(NewAssessmentActivity.REPLY_END),
                    data.getStringExtra(NewAssessmentActivity.REPLY_TYPE),
                    data.getIntExtra(NewAssessmentActivity.REPLY_COURSE_ID,0));
            mAssessmentsViewModel.update(assessment, data.getIntExtra(NewAssessmentActivity.REPLY_ASSESSMENT_ID, -1));
            assessment.setId(data.getIntExtra(NewAssessmentActivity.REPLY_ASSESSMENT_ID, -1));
            updateInterfaceValues(assessment);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.assessment_updated,
                    Toast.LENGTH_LONG).show();
        } else {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.assessment_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
