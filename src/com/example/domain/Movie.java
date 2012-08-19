package com.example.domain;

import java.io.Serializable;
import java.math.BigInteger;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name="data")
public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Element
	private BigInteger id;

	@Element
	private String title;
	
	@Element
	private String type;
	
	@Element
	private boolean available;
	
	@Element
	private String actorName;
	
	@Element
	private String actressName;
	
	@Element
	private String musicDirector;
	
	@Element
	private String flimDirector;
	
	@Element
	private String imageUrl;
	

	public Movie() {
		super();
	}

	public Movie(String title, String type, boolean available,
			String actorName, String actressName, String musicDirector,
			String flimDirector, String imageUrl) {
		super();
		this.title = title;
		this.type = type;
		this.available = available;
		this.actorName = actorName;
		this.actressName = actressName;
		this.musicDirector = musicDirector;
		this.flimDirector = flimDirector;
		this.imageUrl = imageUrl;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean available() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActressName() {
		return actressName;
	}

	public void setActressName(String actressName) {
		this.actressName = actressName;
	}

	public String getMusicDirector() {
		return musicDirector;
	}

	public void setMusicDirector(String musicDirector) {
		this.musicDirector = musicDirector;
	}

	public String getFlimDirector() {
		return flimDirector;
	}

	public void setFlimDirector(String flimDirector) {
		this.flimDirector = flimDirector;
	}
	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", type=" + type
				+ ", isAvailable=" + available + ", actorName=" + actorName
				+ ", actressName=" + actressName + ", musicDirector="
				+ musicDirector + ", flimDirector=" + flimDirector
				+ ", imageUrl=" + imageUrl + "]";
	}

}
