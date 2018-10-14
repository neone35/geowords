package com.github.neone35.geowords.data.source;

import com.github.neone35.geowords.data.models.local.Word;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WordDataSource {

    Flowable<List<Word>> getWords();

    Flowable<Word> getWord(String word);

    Single<Long> insertOrUpdateWord(Word word);

    Single<Integer> deleteAllWords();
}
