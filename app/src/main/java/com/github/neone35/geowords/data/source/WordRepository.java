package com.github.neone35.geowords.data.source;

import com.github.neone35.geowords.data.models.local.Word;

import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public class WordRepository implements WordDataSource {

    @Nullable
    private static WordRepository sInstance = null;

    @NonNull
    private final WordDataSource mWordsLocalDataSource;

    @NonNull
    private final WordDataSource mWordsRemoteDataSource;

    // Prevent direct instantiation.
    private WordRepository(@NonNull WordDataSource wordsLocalDataSource,
                           @NonNull WordDataSource wordsRemoteDataSource) {
        mWordsLocalDataSource = wordsLocalDataSource;
        mWordsRemoteDataSource = wordsRemoteDataSource;
    }

    // Returns the single instance of this class, creating it if necessary.
    public static WordRepository getInstance(@NonNull WordDataSource wordsRemoteDataSource,
                                              @NonNull WordDataSource wordsLocalDataSource) {
        if (sInstance == null) {
            sInstance = new WordRepository(wordsRemoteDataSource, wordsLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<Word>> getWords() {
        return mWordsLocalDataSource.getWords();
    }

    @Override
    public Flowable<Word> getWord(String word) {
        return mWordsLocalDataSource.getWord(word)
                .doOnNext(word1 -> {
                    if (word1.getWord() == null) {
                        mWordsRemoteDataSource.getWord(word);
                    }
                });
    }

    @Override
    public void insertOrUpdateWord(Word word) {
        mWordsLocalDataSource.insertOrUpdateWord(word);
    }

    @Override
    public int deleteAllWords() {
        return mWordsLocalDataSource.deleteAllWords();
    }
}
