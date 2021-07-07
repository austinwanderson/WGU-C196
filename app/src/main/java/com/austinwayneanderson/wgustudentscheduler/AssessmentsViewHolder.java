package com.austinwayneanderson.wgustudentscheduler;

import android.content.Context;
import android.content.Intent;
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
    private final Context context;

    private AssessmentsViewHolder(View assessmentView) {
        super(assessmentView);
        context = assessmentView.getContext();
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
                Intent intent = new Intent(context, AssessmentDetailActivity.class);
                intent.putExtra("ASSESSMENT_ID", b.getText().toString().split(":",2)[0]);
                intent.putExtra("ASSESSMENT_TITLE", b.getText().toString().split(":",2)[1]);
                context.startActivity(intent);
            }
        });
    }

    static AssessmentsViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AssessmentsViewHolder(view);
    }
}
