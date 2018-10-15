package com.github.neone35.geowords;

import android.content.Context;

import com.github.neone35.geowords.data.source.WordRepository;
import com.github.neone35.geowords.data.source.local.LocalDataSource;
import com.github.neone35.geowords.data.source.local.WordDatabase;
import com.github.neone35.geowords.data.source.remote.RemoteDataSource;
import com.github.neone35.geowords.data.source.remote.WordInteractorImpl;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class Injection {

    public static WordRepository provideWordsRepository(@NonNull Context context) {
        // pay attention to the order of sources!
        return WordRepository.getInstance(
                provideLocalDataSource(context, Schedulers.io()),
                provideRemoteDataSource());
    }

    private static RemoteDataSource provideRemoteDataSource() {
        WordInteractorImpl wordInteractorImpl = new WordInteractorImpl();
        return new RemoteDataSource(wordInteractorImpl);
    }

    private static LocalDataSource provideLocalDataSource(Context context, Scheduler scheduler) {
        WordDatabase wordDatabase = WordDatabase.getInstance(context);
        return new LocalDataSource(wordDatabase.wordDao(), scheduler);
    }

}
