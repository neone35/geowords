package com.github.neone35.geowords.data.source.remote;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class RemoteDataSource implements WordDataSource {

    private final WordInteractorImpl mWordInteractorImpl;

    public RemoteDataSource(WordInteractorImpl wordInteractorImpl) {
        mWordInteractorImpl = wordInteractorImpl;
    }

    // not used remotely as only one word is requested at a time
    @Override
    public Flowable<List<Word>> getWords() {
        return null;
    }

    @Override
    public Flowable<Word> getWord(String word) {
        return mWordInteractorImpl.fetchWord(word)
                .toFlowable()
                .map(wordResponse ->
                        new Word(wordResponse.getWord(),
                                wordResponse.getResults().get(0).getPartOfSpeech(),
                                System.currentTimeMillis()));
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
