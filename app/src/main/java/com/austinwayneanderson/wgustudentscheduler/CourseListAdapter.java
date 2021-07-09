package com.austinwayneanderson.wgustudentscheduler;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class CourseListAdapter extends ListAdapter<Course, CoursesViewHolder> {

    public CourseListAdapter(@NonNull DiffUtil.ItemCallback<Course> diffCallback) {
        super(diffCallback);
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CoursesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        Course current = getItem(position);
        holder.bind(current.getCourse());
    }

    static class CourseDiff extends DiffUtil.ItemCallback<Course> {

        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCourse().equals(newItem.getCourse());
        }
    }
}