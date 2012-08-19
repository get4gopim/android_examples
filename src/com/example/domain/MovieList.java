package com.example.domain;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="Movies")
public class MovieList {

	@ElementList(inline=true)
	private List<Movie> data;

	public List<Movie> getData() {
		return data;
	}

	public void setData(List<Movie> data) {
		this.data = data;
	}

}
