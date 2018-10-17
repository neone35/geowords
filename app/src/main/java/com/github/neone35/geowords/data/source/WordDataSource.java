package com.github.neone35.geowords.data.source;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.models.remote.WordResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WordDataSource {

    Flowable<List<Word>> getWords();

    Single<Word> getWord(String word);

    Single<WordResponse> fetchWord(String word);

    void insertOrUpdateWord(Word word);

    int deleteAllWords();
}
