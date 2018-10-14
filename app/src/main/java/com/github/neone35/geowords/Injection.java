package com.github.neone35.geowords;

import android.content.Context;

import com.github.neone35.geowords.data.source.WordRepository;
import com.github.neone35.geowords.data.source.local.LocalDataSource;
import com.github.neone35.geowords.data.source.local.WordDatabase;
import com.github.neone35.geowords.data.source.remote.RemoteDataSource;
import com.github.neone35.geowords.data.source.remote.WordInteractorImpl;

import androidx.annotation.NonNull;

public class Injection {

    public static WordRepository provideTasksRepository(@NonNull Context context) {
        return WordRepository.getInstance(provideRemoteDataSource(context),
                provideLocalDataSource(context));
    }

    private static RemoteDataSource provideRemoteDataSource(Context context) {
        WordInteractorImpl wordInteractorImpl = new WordInteractorImpl();
        return new RemoteDataSource(wordInteractorImpl);
    }

    private static LocalDataSource provideLocalDataSource(Context context) {
        WordDatabase wordDatabase = WordDatabase.getInstance(context);
        return new LocalDataSource(wordDatabase.wordDao());
    }

}
