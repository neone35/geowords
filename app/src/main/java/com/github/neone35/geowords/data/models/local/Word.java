package com.github.neone35.geowords.data.models.local;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "word")
    private String word;
    @ColumnInfo(name = "part_of_speech")
    private String partOfSpeech;
    @ColumnInfo(name = "time_millis")
    private long timeMillis;

    // Constructor used by Room to create Words
    public Word(String word, String partOfSpeech, long timeMillis) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.timeMillis = timeMillis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
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
}
