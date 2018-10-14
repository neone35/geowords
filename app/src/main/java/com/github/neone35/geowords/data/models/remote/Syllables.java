package com.github.neone35.geowords.data.models.remote;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Syllables{

	@SerializedName("count")
	private int count;

	@SerializedName("list")
	private List<String> list;

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