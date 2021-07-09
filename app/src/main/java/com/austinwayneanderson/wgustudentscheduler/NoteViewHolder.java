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

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView noteTitleView;
    private final TextView noteContentView;
    private final Context context;

    private NoteViewHolder(View noteView) {
        super(noteView);
        context = noteView.getContext();
        noteTitleView = noteView.findViewById(R.id.noteTitle);
        noteContentView = noteView.findViewById(R.id.noteContent);
    }

    public void bind(String noteTitle, String noteContents) {
        noteTitleView.setText(noteTitle);
        noteContentView.setText(noteContents);
    }

    static NoteViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
