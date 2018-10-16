package com.github.neone35.geowords.data.models.local;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {

    @PrimaryKey
    @NonNull
    private String word;
    @ColumnInfo(name = "part_of_speech")
    private String partOfSpeech;
    @ColumnInfo(name = "time_millis")
    private long timeMillis;
    @ColumnInfo(name = "lat_lng")
    private LatLng latLng;
    @ColumnInfo(name = "icon_id")
    private int iconId;

    // Constructor used by Room to create Words
    public Word(@NonNull String word, String partOfSpeech, long timeMillis,
                LatLng latLng, int iconId) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.timeMillis = timeMillis;
        this.latLng = latLng;
        this.iconId = iconId;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
