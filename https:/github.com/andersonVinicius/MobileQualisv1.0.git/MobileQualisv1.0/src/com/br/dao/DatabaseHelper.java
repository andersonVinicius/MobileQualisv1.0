package com.br.dao;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLClientInfoException;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DBPATH = "/data/data/com.br.activity/databases";
	private static String DBNAME = "capesfull1.sqlite";
	private Context context;

	public DatabaseHelper(Context context) {

		super(context, "capesfull1.sqlite", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// não utilizado devido ao uso do bd externo
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private boolean checkDataBase() {
		SQLiteDatabase db = null;

		try {
			String path = DBPATH + DBNAME;
			db = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
			db.close();
		} catch (SQLException e) {

		}
		return db != null;
	}

	private void createDataBase() throws Exception {
			boolean exist = checkDataBase();
			
			if(!exist){
				this.getReadableDatabase();
				
				try{
					copyDatabase();
					
				}catch (IOException e){
					throw new Error("Não foi possivel copiar o arquivo");
				}
			}
	}

	private void copyDatabase()throws IOException {
		
		String dbPath = DBPATH + DBNAME;
		OutputStream dbStream = new FileOutputStream(dbPath);
		
		for (int i = 1; i<=23;i++){
			
			//InputStream dbInputStream = context.getAssets().open("linguagens.sqlite");
			InputStream dbInputStream = context.getAssets().open("xa"+i);
			
			byte[] buffer = new byte[1024];
			int length;
			 while ((length = dbInputStream.read(buffer))>0){
				 dbStream.write(buffer,0,length);
			 }
			 dbInputStream.close();
		}
		
		 
		 dbStream.flush();
		 dbStream.close();
		
	}

	public SQLiteDatabase getDatabase() {
		
		try{
			createDataBase();
			
			String path = DBPATH + DBNAME;
			return  SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);		
		}catch(Exception e){
		return this.getWritableDatabase();
		}
	}

}
