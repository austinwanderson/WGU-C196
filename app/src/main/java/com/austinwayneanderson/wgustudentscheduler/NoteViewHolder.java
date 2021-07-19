package com.austinwayneanderson.wgustudentscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView noteTitleView;
    private final TextView noteContentView;
    private final Button noteDetailButton;
    private final Context context;
    private final AlertDialog.Builder builder;

    private NoteViewHolder(View noteView) {
        super(noteView);
        context = noteView.getContext();
        noteTitleView = noteView.findViewById(R.id.noteTitle);
        noteContentView = noteView.findViewById(R.id.noteContent);
        noteDetailButton = noteView.findViewById(R.id.noteDetailBtn);
        builder = new AlertDialog.Builder(this.context);
    }

    public void bind(String noteTitle, String noteContents) {
        noteTitleView.setText(noteTitle);
        noteContentView.setText(noteContents);
        noteDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("What would you like to do?");
                builder.setCancelable(true);
                builder.setPositiveButton("Share", (dialog, id) -> {
                    Button b = (Button) v;
                    CardView c = (CardView) b.getParent();
                    LinearLayout l = (LinearLayout) c.getChildAt(0);
                    TextView titleView = (TextView) l.getChildAt(0);
                    TextView descView = (TextView) l.getChildAt(1);
                    String noteTitle1 = titleView.getText().toString();
                    String noteDesc = descView.getText().toString();

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, noteTitle1);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, noteDesc);

                    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                });
                builder.setNeutralButton("Delete", (dialog, id) -> {
                    dialog.cancel();
                    Button b = (Button) v;
                    CardView c = (CardView) b.getParent();
                    LinearLayout l = (LinearLayout) c.getChildAt(0);
                    TextView titleView = (TextView) l.getChildAt(0);
                    SchedulerRoomDatabase db = SchedulerRoomDatabase.getDatabase(context.getApplicationContext());
                    Note note = db.noteDao().getNoteById(titleView.getText().toString().split(":",2)[0]);
                    db.noteDao().delete(note);
                    Toast.makeText(
                            context.getApplicationContext(),
                            R.string.note_deleted,
                            Toast.LENGTH_LONG).show();
                });
                builder.setNegativeButton("Edit", (dialog, id) -> {
                    dialog.cancel();
                    Button b = (Button) v;
                    CardView c = (CardView) b.getParent();
                    LinearLayout l = (LinearLayout) c.getChildAt(0);
                    TextView titleView = (TextView) l.getChildAt(0);
                    Intent intent = new Intent(context,NewNoteActivity.class);
                    intent.putExtra("EDIT_NOTE_ID", titleView.getText().toString().split(":",2)[0]);
                    context.startActivity(intent);
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    static NoteViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
