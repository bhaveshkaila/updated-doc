package com.example.xmlexample.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Songs implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ElementList(inline = true,name="Song")
	public List<Song> song;
	
	public List<Song> getSong() {
		return song;
	}

	public void setSong(List<Song> song) {
		this.song = song;
	}

}
