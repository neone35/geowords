package com.github.neone35.geowords.data.source.local;

import android.content.Context;

import com.orhanobut.logger.Logger;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class WordDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "wordsDB";
    private static WordDatabase sInstance;

    public static WordDatabase getInstance(Context context) {
//        Logger.d("Getting the database");
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    WordDatabase.class, WordDatabase.DATABASE_NAME).build();
            Logger.d("Made new database");
        }
        return sInstance;
    }


}
