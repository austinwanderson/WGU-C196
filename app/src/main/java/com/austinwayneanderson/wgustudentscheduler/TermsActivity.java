package com.austinwayneanderson.wgustudentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermsActivity extends AppCompatActivity {

    private TermsViewModel mTermsViewModel;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        RecyclerView recyclerView = findViewById(R.id.termsList);
        final TermListAdapter adapter = new TermListAdapter(new TermListAdapter.TermDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);

        mTermsViewModel.getAllTerms().observe(this, terms -> {
            adapter.submitList(terms);
        });

        FloatingActionButton fab = findViewById(R.id.addTermFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermsActivity.this, NewTermActivity.class);
            startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Term term = new Term(data.getStringExtra(NewTermActivity.REPLY_TITLE),
                    data.getStringExtra(NewTermActivity.REPLY_START),
                    data.getStringExtra(NewTermActivity.REPLY_END));
            mTermsViewModel.insert(term);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.term_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}