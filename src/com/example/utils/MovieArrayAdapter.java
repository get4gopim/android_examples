package com.example.utils;

import java.util.List;

import com.example.domain.Movie;

import com.example.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

	private LayoutInflater inflater;

	public MovieArrayAdapter(Context context, List<Movie> planetList) {
		super(context, R.layout.simple_movie_row, R.id.rowTitle, planetList);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Planet to display
		Movie planet = (Movie) this.getItem(position);

		// The child views in each row.
		TextView textDirBy;
		TextView textRowId = null;
		TextView textTitle;
		TextView textMusic;

		// Create a new row view
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.simple_movie_row, null);

			// Find the child views.
			textRowId = (TextView) convertView.findViewById(R.id.rowId);
			textTitle = (TextView) convertView.findViewById(R.id.rowTitle);
			textMusic = (TextView) convertView.findViewById(R.id.rowMusic);
			textDirBy = (TextView) convertView.findViewById(R.id.rowDirBy);
			
			convertView.setTag(new MovieViewHolder(textTitle, textMusic, textDirBy));
		}
		// Reuse existing row view
		else {
			// Because we use a ViewHolder, we avoid having to call
			// findViewById().
			MovieViewHolder viewHolder = (MovieViewHolder) convertView.getTag();
			textTitle = viewHolder.getTextTitle();
			textMusic = viewHolder.getTextMusicBy();
			textDirBy = viewHolder.getTextDirBy();
		}

		textTitle.setText(planet.getTitle());
		textMusic.setText(planet.getMusicDirector());
		textDirBy.setText(planet.getFlimDirector());
		
		if (textRowId != null) {
			textRowId.setText(planet.getId()+"");
		}

		return convertView;
	}
	
	
	public class MovieViewHolder {

	    private TextView textDirBy ;
	    private TextView textTitle ;
	    private TextView textMusicBy ;
	    private long id ;
	    
	    public MovieViewHolder() {}
	    
	    public MovieViewHolder(TextView textTitle, TextView textMusicBy, TextView textDirBy ) {
	      this.textTitle = textTitle ;
	      this.textMusicBy = textMusicBy;
	      this.textDirBy = textDirBy ;
	    }

		public TextView getTextDirBy() {
			return textDirBy;
		}

		public void setTextDirBy(TextView textDirBy) {
			this.textDirBy = textDirBy;
		}

		public TextView getTextTitle() {
			return textTitle;
		}

		public void setTextTitle(TextView textTitle) {
			this.textTitle = textTitle;
		}



		public TextView getTextMusicBy() {
			return textMusicBy;
		}

		public void setTextMusicBy(TextView textMusicBy) {
			this.textMusicBy = textMusicBy;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

	    
	    
	    
	}
	
}
