package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultsItem{

	@SerializedName("partOfSpeech")
	private String partOfSpeech;

	@SerializedName("definition")
	private String definition;

	@SerializedName("derivation")
	private List<String> derivation;

	@SerializedName("typeOf")
	private List<String> typeOf;

	@SerializedName("synonyms")
	private List<String> synonyms;

	public void setPartOfSpeech(String partOfSpeech){
		this.partOfSpeech = partOfSpeech;
	}

	public String getPartOfSpeech(){
		return partOfSpeech;
	}

	public void setDefinition(String definition){
		this.definition = definition;
	}

	public String getDefinition(){
		return definition;
	}

	public void setDerivation(List<String> derivation){
		this.derivation = derivation;
	}

	public List<String> getDerivation(){
		return derivation;
	}

	public void setTypeOf(List<String> typeOf){
		this.typeOf = typeOf;
	}

	public List<String> getTypeOf(){
		return typeOf;
	}

	public void setSynonyms(List<String> synonyms){
		this.synonyms = synonyms;
	}

	public List<String> getSynonyms(){
		return synonyms;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"partOfSpeech = '" + partOfSpeech + '\'' + 
			",definition = '" + definition + '\'' + 
			",derivation = '" + derivation + '\'' + 
			",typeOf = '" + typeOf + '\'' + 
			",synonyms = '" + synonyms + '\'' + 
			"}";
		}
}