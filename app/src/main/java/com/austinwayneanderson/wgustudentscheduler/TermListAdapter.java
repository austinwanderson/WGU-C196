package com.austinwayneanderson.wgustudentscheduler;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class TermListAdapter extends ListAdapter<Term, TermsViewHolder> {

    public TermListAdapter(@NonNull DiffUtil.ItemCallback<Term> diffCallback) {
        super(diffCallback);
    }

    @Override
    public TermsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TermsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TermsViewHolder holder, int position) {
        Term current = getItem(position);
        holder.bind(current.getTerm());
    }

    static class TermDiff extends DiffUtil.ItemCallback<Term> {

        @Override
        public boolean areItemsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            return oldItem.getTerm().equals(newItem.getTerm());
        }
    }
}