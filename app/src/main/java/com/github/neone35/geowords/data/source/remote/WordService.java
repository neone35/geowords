package com.github.neone35.geowords.data.source.remote;

import com.github.neone35.geowords.data.models.remote.WordResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WordService {

    @GET("/words/{word}?mashape-key=X7MmtDfDWEmshGrZETAwmSjpf2qBp1q3O3fjsnIMIe4Hr6pUpy")
    Single<WordResponse> fetchWord(@Path("word") String word);
}
