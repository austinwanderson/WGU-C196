package com.austinwayneanderson.wgustudentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewTermActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditStartDateView;
    private EditText mEditEndDateView;

    private boolean isEditingTerm = false;
    private Term editingTerm;
    public static final String REPLY_TITLE = "com.example.android.termlistsql.TITLE";
    public static final String REPLY_START = "com.example.android.termlistsql.START_DATE";
    public static final String REPLY_END = "com.example.android.termlistsql.END_DATE";
    public static final String REPLY_TERM_ID = "com.example.android.termlistsql.ID";
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        mEditTitleView = findViewById(R.id.edit_term_title);
        mEditStartDateView = findViewById(R.id.edit_term_start_date);
        mEditEndDateView = findViewById(R.id.edit_term_end_date);

        SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(getApplicationContext());

        if (getIntent().getExtras() != null) {
            extras = getIntent().getExtras();
            if (extras.containsKey("EDIT_TERM_ID")) {
                isEditingTerm = true;
                editingTerm = db.termDao().getTermById(extras.getString("EDIT_TERM_ID"));
                mEditTitleView.setText(editingTerm.getTitle());
                mEditStartDateView.setText(editingTerm.getStartDate());
                mEditEndDateView.setText(editingTerm.getEndDate());
            }
        }

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
                if (isEditingTerm) {
                    replyIntent.putExtra(REPLY_TERM_ID, editingTerm.getId());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}