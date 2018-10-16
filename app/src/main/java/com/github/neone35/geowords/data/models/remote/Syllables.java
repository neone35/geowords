package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Generated("com.robohorse.robopojogenerator")
@Parcel
public class Syllables{

	@SerializedName("count")
	int count;

	@SerializedName("list")
	List<String> list;

	// needed by the Parceler library
	public Syllables() {
	}
	public Syllables(int count, List<String> list) {
		this.count = count;
		this.list = list;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setList(List<String> list){
		this.list = list;
	}

	public List<String> getList(){
		return list;
	}

	@Override
 	public String toString(){
		return 
			"Syllables{" + 
			"count = '" + count + '\'' + 
			",list = '" + list + '\'' + 
			"}";
		}
}