package com.example.moviedb;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.domain.Movie;

public class MovieActivity extends Activity {

	private EditText txtTitle;
	private EditText txtActor;
	private EditText txtActress;
	private EditText txtDirBy;
	private EditText txtMusicBy;

	private ImageView imgMovie;
	
	private Movie movie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);

		txtTitle = (EditText) findViewById(R.id.txtTitle);
		txtActor = (EditText) findViewById(R.id.txtActor);
		txtActress = (EditText) findViewById(R.id.txtActress);
		txtDirBy = (EditText) findViewById(R.id.txtDirBy);
		txtMusicBy = (EditText) findViewById(R.id.txtMusicBy);

		imgMovie = (ImageView) findViewById(R.id.imgMovie);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			movie = (Movie) extras.get("movie");
			setMovieInfo(movie);
		} else {
			Toast.makeText(this, "Search string not found!! Refine your search.", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_movie, menu);
		return true;
	}

	private void setMovieInfo(Movie movie) {
		txtTitle.setText(movie.getTitle());
		txtActor.setText(movie.getActorName());
		txtActress.setText(movie.getActressName());
		txtDirBy.setText(movie.getFlimDirector());
		txtMusicBy.setText(movie.getMusicDirector());

		imgMovie.setImageBitmap(getImageBitmap(movie.getImageUrl()));
	}

	private Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Toast.makeText(this, "Error getting bitmap", Toast.LENGTH_SHORT)
					.show();
		}
		return bm;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuBack:
				super.finish();
				break;
			default:
				break;
		}

		return true;
	}
}
