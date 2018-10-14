package com.github.neone35.geowords.data.source;

import com.github.neone35.geowords.data.models.local.Word;

import java.util.List;

import io.reactivex.Flowable;

public interface WordDataSource {

    Flowable<List<Word>> getWords();

    Flowable<Word> getWord(String word);

    void insertOrUpdateWord(Word word);

    int deleteAllWords();
}
