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

public class TermDetailActivity extends AppCompatActivity {

    private static final int EDITED_TERM_ACTIVITY_REQUEST_CODE = 1;
    private Button editTermButton;
    private Button viewCoursesButton;
    private TextView termTitle;
    private TextView termStart;
    private TextView termEnd;
    private TextView termId;
    private TermsViewModel mTermsViewModel;
    private Bundle extras;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_term_item: {
                deleteTerm();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        ActionBar actionBar = getSupportActionBar();

        editTermButton = findViewById(R.id.editTermButton);
        termTitle = findViewById(R.id.termDetailTitle);
        System.out.println("TermTitle " + termTitle);
        termStart = findViewById(R.id.termDetailStart);
        termEnd = findViewById(R.id.termDetailEnd);
        termId = findViewById(R.id.termDetailId);
        viewCoursesButton = findViewById(R.id.viewTermCoursesButton);
        mTermsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);

        extras = getIntent().getExtras();
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Term term = db.termDao().getTermById(extras.getString("TERM_ID"));
        updateInterfaceValues(term);
        editTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditTermActivity();
            }
        });

        viewCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermCoursesActivity();
            }
        });
    }

    private void openTermCoursesActivity() {
        Intent intent = new Intent(TermDetailActivity.this, CoursesActivity.class);
        intent.putExtra("TERM_ID", extras.getString("TERM_ID"));
        startActivity(intent);
    }

    private void updateInterfaceValues(Term term) {
        termTitle.setText("Title: " + term.getTitle());
        termStart.setText("Start Date: " + term.getStartDate());
        termEnd.setText("End Date: " + term.getEndDate());
        termId.setText("Term ID: " + term.getId());
    }

    private void deleteTerm() {
        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());
        Term term = db.termDao().getTermById(extras.getString("TERM_ID"));
        mTermsViewModel.delete(term);
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
        Toast.makeText(
                getApplicationContext(),
                R.string.term_deleted,
                Toast.LENGTH_LONG).show();
    }

    private void openEditTermActivity() {
        Intent intent = new Intent(TermDetailActivity.this, NewTermActivity.class);
        intent.putExtra("EDIT_TERM_ID", extras.getString("TERM_ID"));
        startActivityForResult(intent, EDITED_TERM_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITED_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Term term = new Term(data.getStringExtra(NewTermActivity.REPLY_TITLE),
                    data.getStringExtra(NewTermActivity.REPLY_START),
                    data.getStringExtra(NewTermActivity.REPLY_END));
            mTermsViewModel.update(term, data.getIntExtra(NewTermActivity.REPLY_TERM_ID, -1));
            term.setId(data.getIntExtra(NewTermActivity.REPLY_TERM_ID, -1));
            updateInterfaceValues(term);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.term_updated,
                    Toast.LENGTH_LONG).show();
        } else {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.term_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
