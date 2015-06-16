package com.example.xmlexample.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Song")
public class Song implements Serializable{

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Attribute(name="title",required=false)
	public String songTitle;
	
	@Element(name = "songId",required=false,data=false)
	public String songId;
	
	@Element(name = "songIndex",required=false,data=false)
	public String songIndex;
	
	@Element(name = "ageRating",required=false,data=false)
	public String ageRating;
	
	@Element(name = "categoryIndex",required=false,data=false)
	public String categoryIndex;
	
	@Element(name = "category",required=false,data=false)
	public String category;
	
	@Element(name = "selectRecipient",required=false,data=false)
	public Boolean selectRecipient;
	
	@Element(name = "backingTrackFile",required=false,data=false)
	public String backingTrackFile;
	
	

}
