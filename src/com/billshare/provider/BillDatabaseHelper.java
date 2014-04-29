package com.billshare.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BillDatabaseHelper extends SQLiteOpenHelper {
	
	
	private static final String DATABASE_NAME = "billshareDB.db";
	private static final int DATABASE_VERSION = 1;

	public BillDatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TableBill.onCreate(db);
		TablePayment.onCreate(db);
		TableHousemate.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableBill.onUpgrade(db);
		TablePayment.onUpgrade(db);
		TableHousemate.onUpgrade(db);
	}

}
