package com.github.neone35.geowords.data.source.local;

import android.content.Context;

import com.github.neone35.geowords.data.models.local.Word;
import com.orhanobut.logger.Logger;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

// version number needs to be incremented if schema models change
@Database(entities = {Word.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class WordDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "wordsDB";
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static WordDatabase sInstance;

    public static WordDatabase getInstance(Context context) {
//        Logger.d("Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        WordDatabase.class, WordDatabase.DATABASE_NAME).build();
                Logger.d("Made new database instance");
            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract WordDao wordDao();

}
