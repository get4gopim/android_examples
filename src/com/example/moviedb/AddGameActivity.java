package com.example.moviedb;

import java.util.ArrayList;
import java.util.List;

import com.example.datasource.CommentsDataSource;
import com.example.datasource.Planet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddGameActivity extends Activity {
	
	private CommentsDataSource datasource;
	
	private static String SEPERATOR = ",";
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
    		//Toast.makeText(this, editGame.getGenere(), Toast.LENGTH_LONG).show();
    		
    		EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
    		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        	SeekBar seek = (SeekBar) findViewById(R.id.seekRate);
        	Switch toggleAvail = (Switch) findViewById(R.id.togglebutton);
        	
        	txtTitle.setText(editGame.getName());
        	seek.setProgress(editGame.getRating());
        	toggleAvail.setChecked(editGame.getAvail().equalsIgnoreCase(res.getString(R.string.lblAvailable)));
        	toggleAvail.setSelected(editGame.getAvail().equalsIgnoreCase(res.getString(R.string.lblAvailable)));
        	
        	if (editGame.getGameType().equalsIgnoreCase(res.getString(R.string.optPSOne))) {
    			rg.check(R.id.radio0);
    		} else if (editGame.getGameType().equalsIgnoreCase(res.getString(R.string.optPS2))) {
    			rg.check(R.id.radio1);
    		} else if (editGame.getGameType().equalsIgnoreCase(res.getString(R.string.optPS3))) {
    			rg.check(R.id.radio2);
    		} else if (editGame.getGameType().equalsIgnoreCase(res.getString(R.string.optPSVita))) {
    			rg.check(R.id.radio3);
    		} else if (editGame.getGameType().equalsIgnoreCase(res.getString(R.string.optPC))) {
    			rg.check(R.id.radio4);
    		}
        	
        	if (editGame.getGenere() != null) {
	        	String gameGeners[] = editGame.getGenere().split(SEPERATOR);
	        	
	        	for (String g : gameGeners) {
	        		if (g.equalsIgnoreCase(res.getString(R.string.optAction))) {
	        			CheckBox chk1 = (CheckBox) findViewById(R.id.chk1);
	        			chk1.setChecked(true);
	        		} else if (g.equalsIgnoreCase(res.getString(R.string.optAdv))) {
	        			CheckBox chk2 = (CheckBox) findViewById(R.id.chk2);
	        			chk2.setChecked(true);
	        		} else if (g.equalsIgnoreCase(res.getString(R.string.optRace))) {
	        			CheckBox chk3 = (CheckBox) findViewById(R.id.chk3);
	        			chk3.setChecked(true);
	        		} else if (g.equalsIgnoreCase(res.getString(R.string.optSports))) {
	        			CheckBox chk4 = (CheckBox) findViewById(R.id.chk4);
	        			chk4.setChecked(true);
	        		} else {
	        			CheckBox chk1 = (CheckBox) findViewById(R.id.chk1);
	        			chk1.setChecked(true);
	        		}
	        	}
        	}
        	
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(this, "No options available", Toast.LENGTH_SHORT).show();
        return true;
    }
    
    public void btnSaveHandler(View view) {
    	datasource = new CommentsDataSource(this);
        datasource.open();
        
        String gameGenere = getCheckBoxSelectedString();
        String gameType = getRadioButtonSelected();
        
        Switch toggleAvail = (Switch) findViewById(R.id.togglebutton);
    	EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
    	SeekBar seek = (SeekBar) findViewById(R.id.seekRate);
		int rating = seek.getProgress();
		String avail = toggleAvail.isChecked() ? res.getString(R.string.lblAvailable) : res.getString(R.string.lblNotAvail);
		
		Planet planet = new Planet();
		planet.setName(txtTitle.getText().toString());
		planet.setGameType(gameType);
		planet.setRating(rating);
		planet.setGenere(gameGenere);
		planet.setAvail(avail);
		
		//Toast.makeText(this, planet.toString(), Toast.LENGTH_LONG).show();
		
		if (editGame != null) {
			datasource.saveGameById(planet, editGame.getId());
		} else {
			datasource.addGame(planet);
		}
    	
    	datasource.close();
		finish();
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra("returnKey1", "Game Saved Successfully");
		//data.putExtra("addedGame", addedGame);
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
	    
	    String gameGenere = "";
	    for (int i=0;i<genere.size();i++) {
        	if (i == (genere.size()-1) ) {
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
}
