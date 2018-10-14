package com.github.neone35.geowords.data.source.local;

import com.github.neone35.geowords.data.models.local.Word;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WordDao {

    // Flowable triggers nothing on error; onNext on success / data update
    @Query("SELECT * FROM words WHERE id = :id")
    Flowable<Word> getById(int id);
    @Query("SELECT * FROM words WHERE word = :word")
    Flowable<Word> getByWord(String word);
    @Query("SELECT * FROM words ORDER BY time_millis DESC")
    Flowable<List<Word>> getAll();

    // returns row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Word word);

    // returns number of rows affected
    @Query("DELETE FROM words")
    int deleteAll();
}
