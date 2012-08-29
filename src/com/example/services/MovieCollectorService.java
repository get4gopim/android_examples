package com.example.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import com.example.activities.MovieActivity;
import com.example.activities.R;
import com.example.datasource.MovieWebServiceImpl;
import com.example.domain.Movie;
import com.example.domain.MovieList;

public class MovieCollectorService extends Service {

	private static final String TAG = MovieCollectorService.class
			.getSimpleName();

	private Timer timer;
	private int previousCount = 0;
	private int currentCount = 0;
	private MovieWebServiceImpl movieService;
	private List<Movie> movieList;

	private TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {
			Log.i(TAG, "Timer task doing work");
			movieList = getAllMovies();
			Movie movie = null;
			
			if (movieList != null && movieList.size() > 0) {
				currentCount = movieList.size();
				movie = movieList.get(movieList.size() - 1);
			}

			String msg = currentCount + " :: " + previousCount;
			Log.i(TAG, msg);

			if (currentCount > previousCount) {
				int diff = currentCount - previousCount;
				msg = diff + " Movie(s) newly added";
				createNotification(msg, movie);
				previousCount += diff;
			}

		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Service creating");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		movieService = new MovieWebServiceImpl();
		//movieList = getAllMovies();
		if (movieList != null) {
			previousCount = movieList.size();
		}

		timer = new Timer("MovieCollectorTimer");
		timer.schedule(updateTask, 1000L, 60 * 1000L);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Service destroying");

		timer.cancel();
		timer = null;
	}

	@SuppressWarnings("deprecation")
	public void createNotification(String message, Movie movie) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.movie_search, message, System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Intent intent = new Intent(this, MovieActivity.class);
		intent.putExtra ("movie", movie);
		
		PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(this, "Movie Collector Service", message, activity); // 

		notification.number += 1;
		notificationManager.notify(0, notification);
	}
	
	public List<Movie> getAllMovies() {
		Log.i(TAG, "getAllMovies");
		
		List<Movie> movieList = null;
		MovieList ml = new MovieList();
		
		try {
			final String url = "http://mgallery.cloudfoundry.com/service/movies/";

			// Create a new RestTemplate instance
			Log.i(TAG, "Create a new RestTemplate instance");
			RestTemplate restTemplate = new RestTemplate();

			// Add the Simple XML message converter
			Log.i(TAG, "Add the Simple XML message converter");
			restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

			// Make the HTTP GET request, marshaling the response from XML to an
			Log.i(TAG, "Make the HTTP GET request, marshaling the response from XML to an");
			ml = restTemplate.getForObject(url, MovieList.class);
			
			if (ml != null && ml.getData() != null) {
				
				movieList = ml.getData();
				Log.i(TAG, "ml not null : " + movieList.size());
			} else Log.i(TAG, "ml null");

		} catch (Exception ex) {
			Log.i(TAG, "Exception = " + ex);
			System.out.println(ex.toString() + "::" + ex.getMessage() + "::");
			ex.printStackTrace();
		}
		return movieList;
	}

}
