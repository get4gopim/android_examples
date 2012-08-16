package com.example.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_GENERE, MySQLiteHelper.COLUMN_GAMETYPE, MySQLiteHelper.COLUMN_AVAIL, MySQLiteHelper.COLUMN_RATING };

	public CommentsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		
		database = dbHelper.getWritableDatabase();
		//dbHelper.onCreate(database);
	}

	public void close() {
		dbHelper.close();
	}

	public void addGame(Planet p) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TITLE, p.getName());
		values.put(MySQLiteHelper.COLUMN_GENERE, p.getGenere());
		values.put(MySQLiteHelper.COLUMN_GAMETYPE, p.getGameType());
		values.put(MySQLiteHelper.COLUMN_AVAIL, p.getAvail());
		values.put(MySQLiteHelper.COLUMN_RATING, p.getRating());
		
		long insertId = database.insert(MySQLiteHelper.TABLE_GAMES, null, values);

		/*
		 * Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,
		 * allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
		 * null, null); cursor.moveToFirst(); Planet game =
		 * cursorToComment(cursor); cursor.close(); return game;
		 */
	}
	
	public void saveGameById(Planet p, long id) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TITLE, p.getName());
		values.put(MySQLiteHelper.COLUMN_GENERE, p.getGenere());
		values.put(MySQLiteHelper.COLUMN_GAMETYPE, p.getGameType());
		values.put(MySQLiteHelper.COLUMN_AVAIL, p.getAvail());
		values.put(MySQLiteHelper.COLUMN_RATING, p.getRating());
		
		database.update(MySQLiteHelper.TABLE_GAMES, values, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public Planet getGameById(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Planet game = cursorToComment(cursor);
		cursor.close();
		return game;
	}

	public void deleteGame(Planet game) {
		long id = game.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_GAMES, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Planet> getAllGames() {
		List<Planet> listGames = new ArrayList<Planet>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Planet comment = cursorToComment(cursor);
			listGames.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return listGames;
	}

	private Planet cursorToComment(Cursor cursor) {
		Planet game = new Planet();
		game.setId(cursor.getLong(0));
		game.setName(cursor.getString(1));
		game.setGenere(cursor.getString(2));
		game.setGameType(cursor.getString(3));
		game.setAvail(cursor.getString(4));
		game.setRating(cursor.getInt(5));
		return game;
	}
}