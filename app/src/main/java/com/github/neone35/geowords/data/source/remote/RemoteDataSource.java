package com.github.neone35.geowords.data.source.remote;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.models.remote.WordResponse;
import com.github.neone35.geowords.data.source.WordDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource implements WordDataSource {

    private final WordInteractorImpl mWordInteractorImpl;
    // io thread scheduler
    private Scheduler mScheduler;

    public RemoteDataSource(WordInteractorImpl wordInteractorImpl, Scheduler scheduler) {
        mWordInteractorImpl = wordInteractorImpl;
        mScheduler = scheduler;
    }

    // not used remotely as only one word is requested at a time
    @Override
    public Flowable<List<Word>> getWords() {
        return null;
    }

    @Override
    public Single<Word> getWord(String word) {
        // fetch word and instantly map into new Word object
        return mWordInteractorImpl.fetchWord(word)
                .subscribeOn(mScheduler)
                .map(wordResponse ->
                        new Word(wordResponse.getWord(),
                                wordResponse.getResults().get(0).getPartOfSpeech(),
                                System.currentTimeMillis(), null, 0));
    }

    // get remote response object only (don't convert to local Word)
    @Override
    public Single<WordResponse> fetchWord(String word) {
        return mWordInteractorImpl.fetchWord(word)
                .subscribeOn(mScheduler);
    }

    // not used remotely as word is only fetched from API
    @Override
    public void insertOrUpdateWord(Word word) {

    }

    // not used remotely as word is only fetched from API
    @Override
    public int deleteAllWords() {
        return 0;
    }
}
