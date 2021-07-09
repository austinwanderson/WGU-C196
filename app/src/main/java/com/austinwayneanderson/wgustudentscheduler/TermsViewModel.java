package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TermsViewModel extends AndroidViewModel {
    private TermRepository mTermRepository;
    private LiveData<List<Term>> mAllTerms;

    public TermsViewModel(Application application) {
        super(application);
        mTermRepository = new TermRepository(application);
        mAllTerms = mTermRepository.getAllTerms();
        //mAllTermNotes = mTermRepository.getAllTermNotes();
    }

    LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }

    void insert(Term a) {
        mTermRepository.insert(a);
    }
    void update(Term a, int id) { mTermRepository.update(a, id); }
    void delete(Term a) { mTermRepository.delete(a); }
}
