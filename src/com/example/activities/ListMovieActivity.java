package com.example.activities;

import java.util.List;

import com.example.datasource.MovieWebServiceImpl;
import com.example.domain.Movie;
import com.example.domain.MovieList;
import com.example.activities.R;
import com.example.utils.MovieArrayAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class ListMovieActivity extends Activity {
	
	private ListView movieListView;
	private List<Movie> listMovies;
	private ArrayAdapter<Movie> listAdapter;
	
	private Spinner spinnerSearchBy;
	private EditText txtSearchString;
	
	private static final int REQUEST_CODE = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);
        
        movieListView = (ListView) findViewById( R.id.movieListView );
	    
	    spinnerSearchBy = (Spinner) findViewById(R.id.spinnerSearchBy);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSearchBy.setAdapter (adapter);

		txtSearchString = (EditText) findViewById(R.id.txtSearchString);
        
        //btnSearchHandler(null);
    }

    public void btnSearchHandler(View view) {
    	MovieWebServiceImpl ms = new MovieWebServiceImpl();
		MovieList movieList = ms.getMoviesByKey(spinnerSearchBy.getSelectedItem().toString(), txtSearchString.getText().toString());
    	//MovieList movieList = ms.getMoviesByKey("title", "ala");

		if (movieList != null && movieList.getData() != null && movieList.getData().size() > 0) {
			Toast.makeText(ListMovieActivity.this, movieList.getData().size() + " movie(s) found !!", Toast.LENGTH_SHORT).show();
			
			listMovies = movieList.getData();
		    listAdapter = new MovieArrayAdapter(this, listMovies);
		    movieListView.setAdapter( listAdapter );
		    
		    movieListView.setOnItemClickListener(new OnItemClickListener() {
		    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    		Movie movie = (Movie) movieListView.getItemAtPosition(position);
		    		
		    		Intent i = new Intent(ListMovieActivity.this, MovieActivity.class);
		    		i.putExtra("movie", movie);
		    		startActivityForResult(i, REQUEST_CODE);
		    	}
		    });
		    
		} else {
			Toast.makeText(this, "Search string not found!! Refine your search.", Toast.LENGTH_LONG).show();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_movie, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuBack:
				super.finish();
				break;
			case R.id.menuReset:
				reset();
				break;
			default:
				break;
		}

		return true;
	}
    
    public void reset() {
		spinnerSearchBy.setSelected(false);
		txtSearchString.setText("");
	}
}
