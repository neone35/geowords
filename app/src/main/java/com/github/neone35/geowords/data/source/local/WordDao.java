package com.github.neone35.geowords.data.source.local;

import com.github.neone35.geowords.data.models.local.Word;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WordDao {

    // Flowable triggers nothing on error, onNext on success / data update
    @Query("SELECT * FROM words WHERE id = :id")
    Flowable<Word> getById(int id);
    @Query("SELECT * FROM words ORDER BY time_millis DESC")
    Flowable<Word> getAll();

    // returns row id in onSuccess
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertOne(Word word);
}
