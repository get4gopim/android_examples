package com.example.moviedb;

import java.util.ArrayList;
import java.util.List;

import com.example.datasource.CommentsDataSource;
import com.example.datasource.Planet;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView mainListView;
	private List<Planet> planetList;
	private ArrayAdapter<Planet> listAdapter;
	private CommentsDataSource datasource;
	private Menu menu;
	
	private static final int REQUEST_CODE = 10;
	private static final int DIALOG_ALERT = 10;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		datasource = new CommentsDataSource(this);
		datasource.open();
		
		 // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );
	    
	    // Create and populate planets.
	    /*planets = (Planet[]) getLastNonConfigurationInstance() ;
	    if ( planets == null ) {
	      planets = new Planet[] { 
	          new Planet("Uncharted 1:Drake's Fortune"), new Planet("God Of War III"), new Planet("Uncharted 2:Among Thevies"), 
	          new Planet("Underworld 3"), new Planet("Tomb Raider:Underworld"), new Planet("Uncharted 3:Drakes Deception"), 
	          new Planet("MaxPayne"), new Planet("NFS:Shift Pro"), new Planet("Ice Age 4"),
	          new Planet("Blue Haul"), new Planet("Haumea"), new Planet("Makemake"),
	          new Planet("Eris")
	      };  
	    }*/
	    
	    planetList = datasource.getAllGames(); //new ArrayList<Planet>();
	    
	    if (!(planetList != null && planetList.size() > 0)) {
	    	Toast.makeText(this, "No item(s) in the list. Add items using the option menu.", Toast.LENGTH_LONG).show();
	    }
	    
	    // Set our custom array adapter as the ListView's adapter.
	    listAdapter = new PlanetArrayAdapter(this, planetList);
	    mainListView.setAdapter( listAdapter );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private List<Planet> getSelectedItems() {
		List<Planet> listSelectedGames = new ArrayList<Planet>();
		for (int i=0;i<planetList.size();i++) {
			Planet game = (Planet) mainListView.getAdapter().getItem(i);
			if (game.isChecked()) {
				listSelectedGames.add(game);
			}
		}
		return listSelectedGames;
	}
	
	private void refreshList() {
		planetList = datasource.getAllGames();
	    listAdapter = new PlanetArrayAdapter(this, planetList);
	    mainListView.setAdapter( listAdapter );
	}
	
	private void refreshList(List<Planet> planetList) {
	    listAdapter = new PlanetArrayAdapter(this, planetList);
	    mainListView.setAdapter( listAdapter );
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuitem1:
				Intent intent = new Intent(this, AddGameActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
				break;
			case R.id.menuitem2:
				List<Planet> listSelectedGames = getSelectedItems();
				
				if (listSelectedGames.size() == 0) {
					Toast.makeText(this, "Select atleast one item to delete", Toast.LENGTH_SHORT).show();
				} else {
					showDialog(DIALOG_ALERT);
				}
				break;
			case R.id.menuitem3:
				long gameId = -1;
				List<Planet> listSelectedItems = getSelectedItems();
				if (listSelectedItems.size() > 0) {
					Planet game = (Planet) listSelectedItems.get(0);
					gameId = game.getId();
				}
				
				if (gameId == -1 || listSelectedItems.size() == 0 || listSelectedItems.size() > 1) {
					Toast.makeText(this, "Select one item to edit", Toast.LENGTH_SHORT).show();
				} else {
					Planet editGame = datasource.getGameById( gameId );
					Intent editIndent = new Intent (this, AddGameActivity.class);
					editIndent.putExtra("editGame", editGame);
					startActivityForResult(editIndent, REQUEST_CODE);
				}
				break;
			case R.id.menuitem4:
				for (Planet game : planetList) {
					game.setChecked(true);
				}
				
				menu.getItem(2).setEnabled(false);
				refreshList(planetList);
				break;
			case R.id.menuitem5:
				for (Planet game : planetList) {
					game.setChecked(false);
				}
				
				menu.getItem(2).setEnabled(true);
				refreshList(planetList);
				break;
			case R.id.menuitem10:
				MainActivity.this.finish();
				break;
			default:
				break;
		}

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnKey1")) {
				//Planet game = (Planet) data.getSerializableExtra("addedGame");
				
				planetList = datasource.getAllGames();
			    listAdapter = new PlanetArrayAdapter(this, planetList);
			    mainListView.setAdapter( listAdapter );
				//listAdapter.add(game);
		        
				Toast.makeText(this, data.getExtras().getString("returnKey1"), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ALERT:
			// Create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure to delete the selected item(s)?");
			builder.setCancelable(true);
			builder.setPositiveButton("Yes", new OkOnClickListener());
			builder.setNegativeButton("No, no", new CancelOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return super.onCreateDialog(id);
	}

	private final class CancelOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			
		}
	}

	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			List<Planet> listSelectedGames = getSelectedItems();
			
			for (Planet game : listSelectedGames) {
				datasource.deleteGame(game);
			}
			refreshList();				
			Toast.makeText(MainActivity.this, listSelectedGames.size() +" Game(s) deleted successfully", Toast.LENGTH_SHORT).show();
		}
	}
	
}
