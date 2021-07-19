package com.austinwayneanderson.wgustudentscheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class InstructorViewHolder extends RecyclerView.ViewHolder {

    private final TextView instructorNameView;
    private final TextView instructorEmailView;
    private final TextView instructorPhoneView;
    private final Button instructorDetailBtn;
    private final Context context;
    private final AlertDialog.Builder builder;

    private InstructorViewHolder(View instructorView) {
        super(instructorView);
        context = instructorView.getContext();
        instructorNameView = instructorView.findViewById(R.id.edit_instructor_name);
        instructorEmailView = instructorView.findViewById(R.id.edit_instructor_email);
        instructorPhoneView = instructorView.findViewById(R.id.edit_instructor_phone);
        instructorDetailBtn = instructorView.findViewById(R.id.instructorDetailBtn);
        builder = new AlertDialog.Builder(this.context);
    }

    public void bind(String name, String phone, String email) {
        instructorNameView.setText(name);
        instructorEmailView.setText(phone);
        instructorPhoneView.setText(email);
        instructorDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("What would you like to do?");
                builder.setCancelable(true);
                builder.setNeutralButton("Delete", (dialog, id) -> {
                    dialog.cancel();
                    Button b = (Button) v;
                    CardView c = (CardView) b.getParent();
                    LinearLayout l = (LinearLayout) c.getChildAt(0);
                    TextView nameView = (TextView) l.getChildAt(0);
                    TextView phoneView = (TextView) l.getChildAt(2);
                    TextView emailView = (TextView) l.getChildAt(1);
                    SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(context.getApplicationContext());
                    Instructor instructor = db.instructorDao().getInstructorByDetails(
                            nameView.getText().toString(),
                            phoneView.getText().toString(),
                            emailView.getText().toString());
                    System.out.println(nameView.getText().toString());
                    System.out.println(phoneView.getText().toString());
                    System.out.println(emailView.getText().toString());
                    System.out.println(instructor);
                    db.instructorDao().delete(instructor);
                    Toast.makeText(
                            context.getApplicationContext(),
                            R.string.instructor_deleted,
                            Toast.LENGTH_LONG).show();
                });
                builder.setPositiveButton("Edit", (dialog, id) -> {
                    dialog.cancel();
                    Button b = (Button) v;
                    CardView c = (CardView) b.getParent();
                    LinearLayout l = (LinearLayout) c.getChildAt(0);
                    TextView nameView = (TextView) l.getChildAt(0);
                    TextView phoneView = (TextView) l.getChildAt(2);
                    TextView emailView = (TextView) l.getChildAt(1);
                    SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(context.getApplicationContext());
                    Instructor instructor = db.instructorDao().getInstructorByDetails(
                            nameView.getText().toString(),
                            phoneView.getText().toString(),
                            emailView.getText().toString());
                    Intent intent = new Intent(context,NewInstructorActivity.class);
                    intent.putExtra("EDIT_INSTRUCTOR_ID", String.valueOf(instructor.getId()));
                    context.startActivity(intent);
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    static InstructorViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_instructor_item, parent, false);
        return new InstructorViewHolder(view);
    }
}
