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

class InstructorViewHolder extends RecyclerView.ViewHolder {

    private final TextView instructorNameView;
    private final TextView instructorEmailView;
    private final TextView instructorPhoneView;
    private final Context context;

    private InstructorViewHolder(View instructorView) {
        super(instructorView);
        context = instructorView.getContext();
        instructorNameView = instructorView.findViewById(R.id.edit_instructor_name);
        instructorEmailView = instructorView.findViewById(R.id.edit_instructor_email);
        instructorPhoneView = instructorView.findViewById(R.id.edit_instructor_phone);
    }

    public void bind(String name, String phone, String email) {
        instructorNameView.setText(name);
        instructorEmailView.setText(phone);
        instructorPhoneView.setText(email);
    }

    static InstructorViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_instructor_item, parent, false);
        return new InstructorViewHolder(view);
    }
}
