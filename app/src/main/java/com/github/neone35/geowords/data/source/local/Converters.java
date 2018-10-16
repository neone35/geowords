package com.github.neone35.geowords.data.source.local;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

// converters must be public
@SuppressWarnings("WeakerAccess")
public class Converters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String latlngToString(LatLng latLng) {
        return gson.toJson(latLng);
    }

    @TypeConverter
    public static LatLng stringToLatlng(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<LatLng>() {
        }.getType();

        return gson.fromJson(data, listType);
    }
}
