package com.github.neone35.geowords.data.source;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.models.remote.WordResponse;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Single;

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
            Logger.d("Made new repository instance");
        }
        return sInstance;
    }

    @Override
    public Flowable<List<Word>> getWords() {
        return mWordsLocalDataSource.getWords();
    }

    @Override
    public Flowable<Word> getWord(String word) { return mWordsLocalDataSource.getWord(word); }

    @Override
    public Single<WordResponse> fetchWord(String word) {
        return mWordsRemoteDataSource.fetchWord(word);
    }

    @Override
    public void insertOrUpdateWord(Word word) {
        Logger.d("insertOrUpdateWord is called with word" + word.getWord());
        mWordsLocalDataSource.insertOrUpdateWord(word);
    }

    @Override
    public int deleteAllWords() {
        return mWordsLocalDataSource.deleteAllWords();
    }
}
