package com.austinwayneanderson.wgustudentscheduler;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class InstructorListAdapter extends ListAdapter<Instructor, InstructorViewHolder> {

    public InstructorListAdapter(@NonNull DiffUtil.ItemCallback<Instructor> diffCallback) {
        super(diffCallback);
    }

    @Override
    public InstructorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return InstructorViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(InstructorViewHolder holder, int position) {
        Instructor current = getItem(position);
        holder.bind(current.getName(), current.getPhoneNumber(), current.getEmailAddress());
    }

    static class InstructorDiff extends DiffUtil.ItemCallback<Instructor> {

        @Override
        public boolean areItemsTheSame(@NonNull Instructor oldItem, @NonNull Instructor newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Instructor oldItem, @NonNull Instructor newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}