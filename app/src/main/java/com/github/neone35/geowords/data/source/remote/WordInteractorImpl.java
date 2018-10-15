package com.github.neone35.geowords.data.source.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.neone35.geowords.data.models.remote.WordResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordInteractorImpl implements WordInteractor {

    private WordService wordService;

    public WordInteractorImpl() {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        String WORDS_BASE_URL = "https://wordsapiv1.p.mashape.com/words/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WORDS_BASE_URL)
                // use Gson
                .addConverterFactory(GsonConverterFactory.create())
                // use RX
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okClient)
                .build();
        wordService = retrofit.create(WordService.class);
    }

    @Override
    public Single<WordResponse> fetchWord(String inputWord) {
        return wordService.getWord(inputWord);
    }
}
