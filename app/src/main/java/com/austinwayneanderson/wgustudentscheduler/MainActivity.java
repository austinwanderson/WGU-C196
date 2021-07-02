package com.austinwayneanderson.wgustudentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button assessmentsButton;
    private Button termsButton;
    private Button coursesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assessmentsButton = findViewById(R.id.allAssessmentsButton);
        termsButton = findViewById(R.id.allTermsButton);
        coursesButton = findViewById(R.id.allCoursesButton);
        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssessmentsActivity();
            }
        });

        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsActivity();
            }
        });

        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoursesActivity();
            }
        });
    }

    private void openCoursesActivity() {
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }

    private void openTermsActivity() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    private void openAssessmentsActivity() {
        Intent intent = new Intent(this, AssessmentsActivity.class);
        startActivity(intent);
    }
}