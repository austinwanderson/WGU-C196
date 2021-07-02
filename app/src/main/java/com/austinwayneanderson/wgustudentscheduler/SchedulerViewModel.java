package com.austinwayneanderson.wgustudentscheduler;

import android.app.Application;

public class SchedulerViewModel {

    private DataRepository dRepository;

    //private final LiveData<List<Word>> mAllWords;

    public SchedulerViewModel (Application application) {
        super();
        dRepository = new DataRepository(application);
        //mAllWords = mRepository.getAllWords();
    }

    //LiveData<List<Word>> getAllWords() { return mAllWords; }
    //public void insert(Word word) { mRepository.insert(word); }

}
