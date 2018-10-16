package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Generated("com.robohorse.robopojogenerator")
@Parcel
public class WordResponse{

	@SerializedName("pronunciation")
	Pronunciation pronunciation;

	@SerializedName("word")
	String word;

	@SerializedName("results")
	List<ResultsItem> results;

	@SerializedName("syllables")
	Syllables syllables;

	@SerializedName("frequency")
	double frequency;

    // needed by the Parceler library
    public WordResponse() {
    }
    public WordResponse(Pronunciation pronunciation, String word, List<ResultsItem> results,
                        Syllables syllables, double frequency) {
        this.pronunciation = pronunciation;
        this.word = word;
        this.results = results;
        this.syllables = syllables;
        this.frequency = frequency;
    }

	public void setPronunciation(Pronunciation pronunciation){
		this.pronunciation = pronunciation;
	}

	public Pronunciation getPronunciation(){
		return pronunciation;
	}

	public void setWord(String word){
		this.word = word;
	}

	public String getWord(){
		return word;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setSyllables(Syllables syllables){
		this.syllables = syllables;
	}

	public Syllables getSyllables(){
		return syllables;
	}

	public void setFrequency(double frequency){
		this.frequency = frequency;
	}

	public double getFrequency(){
		return frequency;
	}

	@Override
 	public String toString(){
		return 
			"WordResponse{" + 
			"pronunciation = '" + pronunciation + '\'' + 
			",word = '" + word + '\'' + 
			",results = '" + results + '\'' + 
			",syllables = '" + syllables + '\'' + 
			",frequency = '" + frequency + '\'' + 
			"}";
		}
}