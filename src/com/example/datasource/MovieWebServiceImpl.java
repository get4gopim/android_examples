package com.example.datasource;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.converter.feed.SyndFeedHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Movie;
import com.example.domain.MovieList;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

public class MovieWebServiceImpl {

	public MovieList getMoviesByKey(String searchBy, String value) {
		//System.out.println(searchBy + "::" + value);
		
		MovieList movieList = new MovieList();
		try {
			final String url = "http://mgallery.cloudfoundry.com/service/movies/" + searchBy + "/" + value;

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();

			// Add the Simple XML message converter
			restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

			// Make the HTTP GET request, marshaling the response from XML to an
			movieList = restTemplate.getForObject(url, MovieList.class);

		} catch (Exception ex) {
			//System.out.println(ex.toString());
		}
		return movieList;
	}
	
	public List<Movie> getAllMovies() {
		System.out.println("getAllMovies");
		List<Movie> movieList = null;
		MovieList ml = new MovieList();
		try {
			final String url = "http://mgallery.cloudfoundry.com/service/movies/";

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();

			// Add the Simple XML message converter
			restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

			// Make the HTTP GET request, marshaling the response from XML to an
			ml = restTemplate.getForObject(url, MovieList.class);
			
			if (ml != null && ml.getData() != null) {
				System.out.println("ml not null");
				movieList = ml.getData();
			} else System.out.println("ml null");

		} catch (Exception ex) {
			//System.out.println(ex.toString());
		}
		return movieList;
	}
	
	public Movie getMovieById(BigInteger id) {
		//System.out.println(searchBy + "::" + value);
		Movie movie = null;
		
		try {
			final String url = "http://mgallery.cloudfoundry.com/service/movies/" + id;

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();

			// Add the Simple XML message converter
			restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

			// Make the HTTP GET request, marshaling the response from XML to an
			movie = restTemplate.getForObject(url, Movie.class);

		} catch (Exception ex) {
			//System.out.println(ex.toString());
		}
		return movie;
	}
	
	public SyndFeed getGoogleRss() {
		
		SyndFeed feed = null;
		
		try {
			final String url = "http://news.google.com/news?ned=in&topic=n&output=rss";

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();

			// Add the Simple XML message converter
			restTemplate.getMessageConverters().add(new SyndFeedHttpMessageConverter());

			// Make the HTTP GET request, marshaling the response from XML to an
			feed = restTemplate.getForObject(url, SyndFeed.class);
			
		} catch (Exception ex) {
			System.out.println("feed Error : " + ex.toString());
		}
		
		return feed;
	}

}
