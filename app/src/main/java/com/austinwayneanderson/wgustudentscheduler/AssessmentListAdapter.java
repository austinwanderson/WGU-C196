package com.austinwayneanderson.wgustudentscheduler;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class AssessmentListAdapter extends ListAdapter<Assessment, AssessmentsViewHolder> {

    public AssessmentListAdapter(@NonNull DiffUtil.ItemCallback<Assessment> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AssessmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AssessmentsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AssessmentsViewHolder holder, int position) {
        Assessment current = getItem(position);
        holder.bind(current.getAssessment());
    }

    static class AssessmentDiff extends DiffUtil.ItemCallback<Assessment> {

        @Override
        public boolean areItemsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            return oldItem.getAssessment().equals(newItem.getAssessment());
        }
    }
}