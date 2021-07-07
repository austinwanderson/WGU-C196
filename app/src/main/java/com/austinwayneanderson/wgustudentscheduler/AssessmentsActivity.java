package com.austinwayneanderson.wgustudentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssessmentsActivity extends AppCompatActivity {

    private AssessmentsViewModel mAssessmentsViewModel;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        RecyclerView recyclerView = findViewById(R.id.assessmentList);
        final AssessmentListAdapter adapter = new AssessmentListAdapter(new AssessmentListAdapter.AssessmentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        mAssessmentsViewModel.getAllAssessments().observe(this, assessments -> {
            adapter.submitList(assessments);
        });

        FloatingActionButton fab = findViewById(R.id.addAssessmentFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentsActivity.this, NewAssessmentActivity.class);
            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment assessment = new Assessment(data.getStringExtra(NewAssessmentActivity.REPLY_TITLE),
                    data.getStringExtra(NewAssessmentActivity.REPLY_START),
                    data.getStringExtra(NewAssessmentActivity.REPLY_END),
                    data.getStringExtra(NewAssessmentActivity.REPLY_TYPE),
                    data.getIntExtra(NewAssessmentActivity.REPLY_COURSE_ID,0));
            mAssessmentsViewModel.insert(assessment);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.assessment_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}