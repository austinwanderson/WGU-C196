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

class TermsViewHolder extends RecyclerView.ViewHolder {

    private final Button termDetailButton;
    private final Context context;

    private TermsViewHolder(View termView) {
        super(termView);
        context = termView.getContext();
        termDetailButton = termView.findViewById(R.id.detailBtn);
    }

    public void bind(String text) {
        termDetailButton.setText(text);
        termDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                System.out.println(b.getText());
                Intent intent = new Intent(context, TermDetailActivity.class);
                intent.putExtra("TERM_ID", b.getText().toString().split(":",2)[0]);
                intent.putExtra("TERM_TITLE", b.getText().toString().split(":",2)[1]);
                context.startActivity(intent);
            }
        });
    }

    static TermsViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new TermsViewHolder(view);
    }
}
