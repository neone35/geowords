package com.github.neone35.geowords.data.models.remote;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Generated("com.robohorse.robopojogenerator")
@Parcel
public class Pronunciation{

	@SerializedName("all")
	String all;

    // needed by the Parceler library
    public Pronunciation() {
    }
    public Pronunciation(String all) {
        this.all = all;
    }

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