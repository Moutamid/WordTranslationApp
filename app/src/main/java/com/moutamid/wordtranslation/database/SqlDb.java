package com.moutamid.wordtranslation.database;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moutamid.wordtranslation.Model.WordsModel;


public class SqlDb extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "flashcardDB";
	private static final String TABLE_FLASH_CARD = "CardTable";
	private static final int DB_VERSION = 1; 
	private static final String COLUMN_PK = "_id";
	private static final String COLUMN_WORD = "word";
	private static final String COLUMN_LETTER = "letter";
	private static final String COLUMN_TIMESTAMP = "date";
	private static final String COLUMN_SEEN = "seen";
	private static final String COLUMN_INTERVAL = "interval";
	private SQLiteDatabase db;

	public SqlDb(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createFlashCardTable = "CREATE TABLE "+TABLE_FLASH_CARD+" ("+
				COLUMN_PK+" INTEGER PRIMARY KEY, " +COLUMN_WORD+" TEXT, " +
				COLUMN_LETTER+" TEXT, " + COLUMN_TIMESTAMP +" TEXT, " + COLUMN_SEEN +" BOOLEAN, " +
				COLUMN_INTERVAL+" INTEGER )";
		db.execSQL(createFlashCardTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASH_CARD);
        // Create tables again
        onCreate(db);
	}
	
	private SQLiteDatabase getWDB(){
		return this.getWritableDatabase();
	}



	public void addCard(WordsModel model){
		db = this.getWDB();
		ContentValues values = new ContentValues();
		values.put(COLUMN_WORD, model.getWord());
		values.put(COLUMN_LETTER, model.getLetter());
		values.put(COLUMN_TIMESTAMP, model.getTimestamp());
		values.put(COLUMN_SEEN, model.isSeen());
		values.put(COLUMN_INTERVAL,model.getInterval());
		db.insert(TABLE_FLASH_CARD, null, values);
		db.close();
	}
	@SuppressLint("Range")
	public int getCardId(String letter){
		String query = "SELECT _id FROM " + TABLE_FLASH_CARD + " WHERE " +
				COLUMN_LETTER + " = '" + letter + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(query, null);
		int id=-1;
		if(res!=null&&res.moveToFirst()) {
			id = res.getInt(res.getColumnIndex(COLUMN_PK));
		}
		return id;
	}

	@SuppressLint("Range")
	public WordsModel getCard(int no){
		String query = "SELECT * FROM " + TABLE_FLASH_CARD + " WHERE " +
				COLUMN_PK + " = '" + no + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(query, null);
		WordsModel model = null;
		if(res!=null&&res.moveToFirst()) {
			model = new WordsModel();
			model.setWord(res.getString(res.getColumnIndex(COLUMN_WORD)));
			model.setLetter(res.getString(res.getColumnIndex(COLUMN_LETTER)));
		}
		return model;
	}


	public List<WordsModel> getAllCard(){
		List<WordsModel> list = new ArrayList<WordsModel>();

		db = this.getWDB();
		Cursor c = db.rawQuery("SELECT * FROM "+TABLE_FLASH_CARD, null);

		if(c.moveToFirst()){
			do {
				WordsModel no = new WordsModel();
				no.setNo(Integer.parseInt(c.getString(0)));
				no.setWord(c.getString(1));
				no.setLetter(c.getString(2));
				no.setTimestamp(c.getString(3));
				no.setSeen(Boolean.parseBoolean(c.getString(4)));
				no.setInterval(Integer.parseInt(c.getString(5)));
				list.add(no);

			} while (c.moveToNext());
		}
		db.close();
		return list;
	}


	public void updatePicture(int id, boolean seen,String date,int interval){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_SEEN,seen);
		contentValues.put(COLUMN_TIMESTAMP,date);
		contentValues.put(COLUMN_INTERVAL,interval);
		db.update(TABLE_FLASH_CARD, contentValues, COLUMN_PK + " = ? " ,
				new String[]{String.valueOf(id)});
		db.close();
	}


	/*public void deleteCategory(int id) {

		// on below line we are creating
		// a variable to write our database.
		db = this.getWDB();

		// on below line we are calling a method to delete our
		// course and we are comparing it with our course name.
		db.delete(TABLE_CATEGORY,COLUMN_CATEGORY_ID + " = ? " ,
				new String[]{String.valueOf(id)});
		db.close();
	}*/


	public void dropTable() {
		db = this.getWDB();
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASH_CARD);

		// Create tables again
		onCreate(db);
		db.close();
	}

}
