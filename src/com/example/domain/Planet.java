package com.example.domain;

import java.io.Serializable;

public class Planet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean checked = false ;
	
	private String name = "" ;
    private String genere = "";
    private String gameType;
    private String avail;
    private int rating;
    private String relDate;
    
    public Planet() {}
    
    private long id;
    
    public Planet( String name ) {
      this.name = name ;
      this.genere = "PS3";
    }
    
    public Planet( String name, boolean checked ) {
      this.name = name ;
      this.checked = checked ;
    }
    
    
    
    public String getGameType() {
		return gameType;
	}

	public String getRelDate() {
		return relDate;
	}

	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public boolean isChecked() {
      return checked;
    }
    public void setChecked(boolean checked) {
      this.checked = checked;
    }
    @Override
	public String toString() {
		return "Planet [checked=" + checked + ", name=" + name + ", genere="
				+ genere + ", gameType=" + gameType + ", avail=" + avail
				+ ", rating=" + rating + ", id=" + id + "]";
	}
    public void toggleChecked() {
      checked = !checked ;
    }

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
    
    
}
