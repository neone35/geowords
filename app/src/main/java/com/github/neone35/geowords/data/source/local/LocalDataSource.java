package com.github.neone35.geowords.data.source.local;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordDataSource;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource implements WordDataSource {

    private final WordDao mWordDao;
    // io thread scheduler
    private Scheduler mScheduler;

    public LocalDataSource(WordDao wordDao, Scheduler scheduler) {
        mWordDao = wordDao;
        mScheduler = scheduler;
    }

    @Override
    public Flowable<List<Word>> getWords() {
        return mWordDao.getAll();
    }

    @Override
    public Flowable<Word> getWord(String word) {
        return mWordDao.getByWord(word);
    }

    @Override
    public void insertOrUpdateWord(Word word) {
        // Reactive version of Runnable
        Completable.fromAction(() -> mWordDao.insertOne(word))
                .subscribeOn(mScheduler)
                .subscribe();
    }

    @Override
    public int deleteAllWords() {
        return mWordDao.deleteAll();
    }
}
