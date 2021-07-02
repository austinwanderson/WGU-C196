package com.austinwayneanderson.wgustudentscheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AssessmentsViewHolder extends RecyclerView.ViewHolder {

    //private final TextView assessmentItemView;
    private final Button assessmentDetailButton;

    private AssessmentsViewHolder(View assessmentView) {
        super(assessmentView);
        //assessmentItemView = assessmentView.findViewById(R.id.textView);
        assessmentDetailButton = assessmentView.findViewById(R.id.detailBtn);
    }

    public void bind(String text) {
        assessmentDetailButton.setText(text);
        assessmentDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                System.out.println(b.getText());
                openAssessmentDetailActivity();
            }
        });
    }

    private void openAssessmentDetailActivity() {
    }

    static AssessmentsViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AssessmentsViewHolder(view);
    }
}
