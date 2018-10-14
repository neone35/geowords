package com.github.neone35.geowords.data.source.local;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordDataSource;

import java.util.List;

import io.reactivex.Flowable;

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
    public void insertOrUpdateWord(Word word) {
       mWordDao.insertOne(word);
    }

    @Override
    public int deleteAllWords() {
        return mWordDao.deleteAll();
    }
}
