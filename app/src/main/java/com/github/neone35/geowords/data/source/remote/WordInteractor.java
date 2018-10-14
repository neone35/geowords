package com.github.neone35.geowords.data.source.remote;

import com.github.neone35.geowords.data.models.remote.WordResponse;

import io.reactivex.Single;

public interface WordInteractor {
    Single<WordResponse> fetchWord(String inputWord);
}
