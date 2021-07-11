package com.austinwayneanderson.wgustudentscheduler;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView noteTitleView;
    private final TextView noteContentView;
    private final Button noteShareButton;
    private final Context context;

    private NoteViewHolder(View noteView) {
        super(noteView);
        context = noteView.getContext();
        noteTitleView = noteView.findViewById(R.id.noteTitle);
        noteContentView = noteView.findViewById(R.id.noteContent);
        noteShareButton = noteView.findViewById(R.id.shareBtn);
    }

    public void bind(String noteTitle, String noteContents) {
        noteTitleView.setText(noteTitle);
        noteContentView.setText(noteContents);
        noteShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                CardView c = (CardView) b.getParent();
                LinearLayout l = (LinearLayout) c.getChildAt(0);
                TextView titleView = (TextView) l.getChildAt(0);
                TextView descView = (TextView) l.getChildAt(1);
                String noteTitle = titleView.getText().toString();
                String noteDesc = descView.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, noteTitle);
                emailIntent.putExtra(Intent.EXTRA_TEXT, noteDesc);

                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }

    static NoteViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
