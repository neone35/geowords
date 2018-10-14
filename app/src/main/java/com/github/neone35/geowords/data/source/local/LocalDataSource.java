package com.github.neone35.geowords.data.source.local;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordDataSource;
import com.github.neone35.geowords.data.source.remote.RemoteDataSource;

import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalDataSource implements WordDataSource {

    private final WordDao mWordDao;

    public LocalDataSource(WordDao wordDao) {
        mWordDao = wordDao;
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
    public Single<Long> insertOrUpdateWord(Word word) {
       return mWordDao.insertOne(word);
    }

    @Override
    public Single<Integer> deleteAllWords() {
        return mWordDao.deleteAll();
    }
}
