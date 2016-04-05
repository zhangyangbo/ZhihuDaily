package com.example.ex00_day_Mole;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
    //因为不能直接保存数组

import android.support.v7.appcompat.R.integer;
@Table(name = "images")
public class StoryImages extends Model {
	@Column(name="story_id")
     private Integer id;
	@Column 
	private String imgurl;
	
	
	
	public Integer getStroyId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
	
	
	
	
	

	
}
