package com.ericliu.billshare.provider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
import android.database.sqlite.SQLiteDatabase;

public class TableBill {

	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table " 
			+ TABLE_BILL
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_TYPE + " text, "
			+ COL_AMOUNT +  " numeric not null, "
			+ COL_BILLING_START + " datetime, "
			+ COL_BILLING_END + " datetime, "
			+ COL_DUE_DATE + " datetime,  "
			+ COL_PAID + " boolean, "
			+ COL_DELETED + " boolean not null default 0 "
			+ " ); "
			;
	
	// create view


	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_BILL);
		onCreate(db);
	}

}
