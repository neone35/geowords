package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Generated("com.robohorse.robopojogenerator")
@Parcel
public class ResultsItem{

	@SerializedName("partOfSpeech")
	String partOfSpeech;

	@SerializedName("definition")
	String definition;

	@SerializedName("derivation")
	List<String> derivation;

	@SerializedName("typeOf")
	List<String> typeOf;

	@SerializedName("synonyms")
	List<String> synonyms;

    // needed by the Parceler library
    public ResultsItem() {
    }
    public ResultsItem(String partOfSpeech, String definition, List<String> derivation,
                       List<String> typeOf, List<String> synonyms) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.derivation = derivation;
        this.typeOf = typeOf;
        this.synonyms = synonyms;
    }

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