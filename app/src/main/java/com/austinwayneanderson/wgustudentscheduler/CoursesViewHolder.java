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

class CoursesViewHolder extends RecyclerView.ViewHolder {

    private final Button courseDetailButton;
    private final Context context;

    private CoursesViewHolder(View courseView) {
        super(courseView);
        context = courseView.getContext();
        courseDetailButton = courseView.findViewById(R.id.detailBtn);
    }

    public void bind(String text) {
        courseDetailButton.setText(text);
        courseDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                System.out.println(b.getText());
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("COURSE_ID", b.getText().toString().split(":",2)[0]);
                intent.putExtra("COURSE_TITLE", b.getText().toString().split(":",2)[1]);
                context.startActivity(intent);
            }
        });
    }

    static CoursesViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new CoursesViewHolder(view);
    }
}
