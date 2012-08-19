package com.example.datasource;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.domain.MovieList;

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

}
