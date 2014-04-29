package com.billshare.provider;

import static com.billshare.provider.DatabaseConstants.*;

import java.sql.SQLException;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class BillProvider extends ContentProvider {

	public static final String AUTH = "com.billshare.provdier";

	public static final Uri BILL_URI = Uri.parse("content://" + AUTH + "/bill");
	public static final Uri HOUSEMATE_URI = Uri.parse("content://" + AUTH
			+ "/housemate");
	public static final Uri PAYMENT_URI = Uri.parse("content://" + AUTH
			+ "/payment");

	// Basic tables
	private static final int BILLS = 1;
	private static final int BILL_ID = 10;

	private static final int HOUSEMATES = 2;
	private static final int HOUSEMATE_ID = 20;

	private static final int PAYMENTS = 3;
	private static final int PAYMENT_ID = 30;

	private static final UriMatcher URI_MATCHER;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTH, "bill", BILLS);
		URI_MATCHER.addURI(AUTH, "bill/#", BILL_ID);

		URI_MATCHER.addURI(AUTH, "housemate", HOUSEMATES);
		URI_MATCHER.addURI(AUTH, "housemate/#", HOUSEMATE_ID);

		URI_MATCHER.addURI(AUTH, "payment", PAYMENTS);
		URI_MATCHER.addURI(AUTH, "payment/#", PAYMENT_ID);
	}

	private BillDatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new BillDatabaseHelper(getContext().getApplicationContext());
		return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		int uriMatch = URI_MATCHER.match(uri);

		switch (uriMatch) {
		case BILLS:

			qb.setTables(TABLE_BILL);
			break;

		case BILL_ID:

			qb.setTables(TABLE_BILL);
			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );
			break;
			
			
		case HOUSEMATES:

			qb.setTables(TABLE_HOUSEMATE);
			break;

		case HOUSEMATE_ID:

			qb.setTables(TABLE_HOUSEMATE);
			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );
			break;
			
			
		case PAYMENTS:

			qb.setTables(TABLE_PAYMENT);
			break;

		case PAYMENT_ID:

			qb.setTables(TABLE_PAYMENT);
			qb.appendWhere(COL_ROWID + " = " + uri.getLastPathSegment() );
			break;

		default:
			throw new IllegalArgumentException(" Unknow URL "+ uri);
		}

		Cursor c = qb.query(dbHelper.getReadableDatabase(), projection,
				selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);

		return c;
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowID = 0;
		Uri newUri;
		int uriMatch = URI_MATCHER.match(uri);
		
		switch (uriMatch) {
		case BILLS:
			rowID = db.insert(TABLE_BILL, null, values);
			newUri = ContentUris.withAppendedId(BILL_URI, rowID);
			break;

			
			
		case HOUSEMATES:
			rowID = db.insert(TABLE_HOUSEMATE, null, values);
			newUri = ContentUris.withAppendedId(HOUSEMATE_URI, rowID);
			break;

			
			
		case PAYMENTS:
			rowID = db.insert(TABLE_PAYMENT, null, values);
			newUri = ContentUris.withAppendedId(PAYMENT_URI, rowID);
			break;


		default:
			throw new IllegalArgumentException(" Unknow URL "+ uri);
		}
		
		
		


			getContext().getContentResolver().notifyChange(uri, null);
		return rowID > 0? newUri : null;

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {


		getContext().getContentResolver().notifyChange(uri, null);

		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String segment; // contains rowId
		int count;
		int uriMatch = URI_MATCHER.match(uri);
		
		switch (uriMatch) {
		case BILLS:
			count = db.update(TABLE_BILL, values, selection, selectionArgs);
			break;

		case BILL_ID:
			segment = uri.getLastPathSegment();
			count = db.update(TABLE_BILL, values, "_id=" + segment + selection, selectionArgs);
			break;
			
			
		case HOUSEMATES:
			count = db.update(TABLE_HOUSEMATE, values, selection, selectionArgs);
			break;

		case HOUSEMATE_ID:
			segment = uri.getLastPathSegment();
			count = db.update(TABLE_HOUSEMATE, values, "_id=" + segment + selection, selectionArgs);
			break;
			
			
		case PAYMENTS:
			count = db.update(TABLE_PAYMENT, values, selection, selectionArgs);
			break;

		case PAYMENT_ID:
			segment = uri.getLastPathSegment();
			count = db.update(TABLE_PAYMENT, values, "_id=" + segment + selection, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException(" Unknow URL "+ uri);
		}
		
		

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
