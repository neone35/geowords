package com.github.neone35.geowords.data.source.remote;

import javax.annotation.Nullable;

public class NetworkSource {

    @Nullable
    private static NetworkSource sInstance;

    public static NetworkSource getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkSource();
        }
        return sInstance;
    }

}
