package com.example.moviedb;

import java.util.List;

import com.example.datasource.Planet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PlanetArrayAdapter extends ArrayAdapter<Planet> {

	private LayoutInflater inflater;

	public PlanetArrayAdapter(Context context, List<Planet> planetList) {
		super(context, R.layout.simplerow, R.id.rowGameName, planetList);
		// Cache the LayoutInflate to avoid asking for a new one each time.
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Planet to display
		Planet planet = (Planet) this.getItem(position);

		// The child views in each row.
		CheckBox checkBox;
		TextView textRowId = null;
		TextView textTitle;
		TextView textGenere;

		// Create a new row view
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.simplerow, null);

			// Find the child views.
			textRowId = (TextView) convertView.findViewById(R.id.rowId);
			textTitle = (TextView) convertView.findViewById(R.id.rowGameName);
			textGenere = (TextView) convertView.findViewById(R.id.rowGenere);
			checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
			
			convertView.setTag(new PlanetViewHolder(textTitle, textGenere, checkBox));

			// If CheckBox is toggled, update the planet it is tagged with.
			checkBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Planet planet = (Planet) cb.getTag();
					planet.setChecked(cb.isChecked());
				}
			});
		}
		// Reuse existing row view
		else {
			// Because we use a ViewHolder, we avoid having to call
			// findViewById().
			PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
			checkBox = viewHolder.getCheckBox();
			textTitle = viewHolder.getTextView();
			textGenere = viewHolder.getTextGenere();
		}

		// Tag the CheckBox with the Planet it is displaying, so that we can
		// access the planet in onClick() when the CheckBox is toggled.
		checkBox.setTag(planet);

		// Display planet data
		checkBox.setChecked(planet.isChecked());
		textTitle.setText(planet.getName());
		textGenere.setText(planet.getGenere());
		if (textRowId != null) {
			textRowId.setText(planet.getId()+"");
		}

		return convertView;
	}
}
