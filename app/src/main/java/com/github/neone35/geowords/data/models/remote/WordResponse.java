package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WordResponse{

	@SerializedName("pronunciation")
	private Pronunciation pronunciation;

	@SerializedName("word")
	private String word;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("syllables")
	private Syllables syllables;

	@SerializedName("frequency")
	private double frequency;

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