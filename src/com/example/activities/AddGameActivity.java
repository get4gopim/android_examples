package com.example.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.datasource.CommentsDataSource;
import com.example.domain.Planet;
import com.example.activities.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

public class AddGameActivity extends Activity {

	private CommentsDataSource datasource;

	private static String SEPERATOR = ", ";
	private Resources res;
	private Planet editGame;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		res = getResources();

		editGame = (Planet) getIntent().getSerializableExtra("editGame");
		setView();
	}

	private void setView() {
		if (editGame != null) {
			// Toast.makeText(this, editGame.getGenere(),
			// Toast.LENGTH_LONG).show();
			setTitle(editGame.getName());

			EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
			EditText txtRelDate = (EditText) findViewById(R.id.txtRelDate);
			RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
			SeekBar seek = (SeekBar) findViewById(R.id.seekRate);
			Switch toggleAvail = (Switch) findViewById(R.id.togglebutton);

			txtTitle.setText(editGame.getName());
			txtRelDate.setText(editGame.getRelDate());
			seek.setProgress(editGame.getRating());
			toggleAvail.setChecked(editGame.getAvail().equalsIgnoreCase(
					res.getString(R.string.lblAvailable)));
			toggleAvail.setSelected(editGame.getAvail().equalsIgnoreCase(
					res.getString(R.string.lblAvailable)));

			if (editGame.getGameType().equalsIgnoreCase(
					res.getString(R.string.optPSOne))) {
				rg.check(R.id.radio0);
			} else if (editGame.getGameType().equalsIgnoreCase(
					res.getString(R.string.optPS2))) {
				rg.check(R.id.radio1);
			} else if (editGame.getGameType().equalsIgnoreCase(
					res.getString(R.string.optPS3))) {
				rg.check(R.id.radio2);
			} else if (editGame.getGameType().equalsIgnoreCase(
					res.getString(R.string.optPSVita))) {
				rg.check(R.id.radio3);
			} else if (editGame.getGameType().equalsIgnoreCase(
					res.getString(R.string.optPC))) {
				rg.check(R.id.radio4);
			}

			if (editGame.getGenere() != null) {
				String gameGeners[] = editGame.getGenere().split(SEPERATOR);

				for (String g : gameGeners) {
					if (g.equalsIgnoreCase(res.getString(R.string.optAction))) {
						CheckBox chk1 = (CheckBox) findViewById(R.id.chk1);
						chk1.setChecked(true);
					} else if (g.equalsIgnoreCase(res
							.getString(R.string.optAdv))) {
						CheckBox chk2 = (CheckBox) findViewById(R.id.chk2);
						chk2.setChecked(true);
					} else if (g.equalsIgnoreCase(res
							.getString(R.string.optRace))) {
						CheckBox chk3 = (CheckBox) findViewById(R.id.chk3);
						chk3.setChecked(true);
					} else if (g.equalsIgnoreCase(res
							.getString(R.string.optSports))) {
						CheckBox chk4 = (CheckBox) findViewById(R.id.chk4);
						chk4.setChecked(true);
					} else if (g.equalsIgnoreCase(res.getString(R.string.opt3d))) {
						( (CheckBox) findViewById(R.id.chk5) ).setChecked(true);
					} else if (g.equalsIgnoreCase(res.getString(R.string.optMove))) {
						( (CheckBox) findViewById(R.id.chk6) ).setChecked(true);
					}
				}
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_game, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuSave:
				btnSaveHandler (null);
				break;
			case R.id.menuCancel:
				btnCancelHandler (null);
				break;
			default:
				break;
		}

		return true;
	}

	public void btnSaveHandler(View view) {
		datasource = new CommentsDataSource(this);
		datasource.open();

		String gameGenere = getCheckBoxSelectedString();
		String gameType = getRadioButtonSelected();

		Switch toggleAvail = (Switch) findViewById(R.id.togglebutton);
		EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
		EditText txtRelDate = (EditText) findViewById(R.id.txtRelDate);
		SeekBar seek = (SeekBar) findViewById(R.id.seekRate);
		int rating = seek.getProgress();
		String avail = toggleAvail.isChecked() ? res
				.getString(R.string.lblAvailable) : res
				.getString(R.string.lblNotAvail);

		Planet planet = new Planet();
		planet.setName(txtTitle.getText().toString());
		planet.setGameType(gameType);
		planet.setRating(rating);
		planet.setGenere(gameGenere);
		planet.setAvail(avail);
		planet.setRelDate(txtRelDate.getText().toString());

		// Toast.makeText(this, planet.toString(), Toast.LENGTH_LONG).show();

		if (editGame != null) {
			datasource.saveGameById(planet, editGame.getId());
		} else {
			datasource.addGame(planet);
		}

		datasource.close();
		returnResult();
	}

	private void returnResult() {
		Intent data = new Intent();
		data.putExtra("returnKey1", "Game Saved Successfully");
		setResult(RESULT_OK, data);
		super.finish();
	}

	public void btnCancelHandler(View view) {
		super.finish();
	}

	public String getCheckBoxSelectedString() {
		List<String> genere = new ArrayList<String>();

		CheckBox chk1 = (CheckBox) findViewById(R.id.chk1);
		CheckBox chk2 = (CheckBox) findViewById(R.id.chk2);
		CheckBox chk3 = (CheckBox) findViewById(R.id.chk3);
		CheckBox chk4 = (CheckBox) findViewById(R.id.chk4);

		if (chk1.isChecked()) {
			genere.add(res.getString(R.string.optAction));
		}
		if (chk2.isChecked()) {
			genere.add(res.getString(R.string.optAdv));
		}
		if (chk3.isChecked()) {
			genere.add(res.getString(R.string.optRace));
		}
		if (chk4.isChecked()) {
			genere.add(res.getString(R.string.optSports));
		}
		if ( ((CheckBox) findViewById(R.id.chk5)).isChecked()) {
			genere.add(res.getString(R.string.opt3d));
		}
		if ( ((CheckBox) findViewById(R.id.chk6)).isChecked()) {
			genere.add(res.getString(R.string.optMove));
		}

		String gameGenere = "";
		for (int i = 0; i < genere.size(); i++) {
			if (i == (genere.size() - 1)) {
				gameGenere += (String) genere.get(i);
			} else {
				gameGenere += (String) genere.get(i) + SEPERATOR;
			}
		}

		return gameGenere;
	}

	private String getRadioButtonSelected() {
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		int radioId = rg.getCheckedRadioButtonId();
		String gameType = "";
		if (radioId == R.id.radio0) {
			gameType = res.getString(R.string.optPSOne);
		} else if (radioId == R.id.radio1) {
			gameType = res.getString(R.string.optPS2);
		} else if (radioId == R.id.radio2) {
			gameType = res.getString(R.string.optPS3);
		} else if (radioId == R.id.radio3) {
			gameType = res.getString(R.string.optPSVita);
		} else if (radioId == R.id.radio4) {
			gameType = res.getString(R.string.optPC);
		}
		return gameType;
	}

	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			// Enable vibrate
		} else {
			// Disable vibrate
		}
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			EditText textDate = (EditText) findViewById(R.id.txtRelDate);
			textDate.setText(view.getDayOfMonth() + "/" + (month+1) + "/" + view.getYear());
		}
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(this.getFragmentManager(), "datePicker");
	}
}
