package com.github.neone35.geowords.data.models.remote;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Pronunciation{

	@SerializedName("all")
	private String all;

	public void setAll(String all){
		this.all = all;
	}

	public String getAll(){
		return all;
	}

	@Override
 	public String toString(){
		return 
			"Pronunciation{" + 
			"all = '" + all + '\'' + 
			"}";
		}
}