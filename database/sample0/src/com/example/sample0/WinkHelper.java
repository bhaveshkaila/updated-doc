package com.example.sample0;

/**
 * Created by bhavesh.kaila on 31/7/13.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WinkHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "WinkDB.db";

	private SQLiteDatabase DB;
	private final Context context;

    /**
     *  constructor of this class will be called to create new database OR open existing database
     *  to perform all database operations
     *
     * @param context   reference to class that extends Activity to access this WinkHelper Class
     */

	public WinkHelper(Context context) {
		super(context, DB_NAME, null, 1);

		this.context = context;
		try {
			openDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getDbFilename(Context context) {
		return "/data/data/" + "com/sample" + "/databases/" + DB_NAME;
	}

    /* to open database*/
	public void openDataBase() throws SQLException {
		if (DB == null || !DB.isOpen())
			// DB = SQLiteDatabase.openDatabase(getDbFilename(context), null,
			// SQLiteDatabase.OPEN_READWRITE);
			DB = this.getWritableDatabase();
	}

	@Override
	public synchronized void close() {
		if (DB != null) {
			DB.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE BHAVESH (bhavesh,text)" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

    /**
     * call made to insert chat detail into internal database
     *
     * @param messageStatus
     * @param messageDateTime   message time in millisecond
     * @param messageText       original message
     * @param ImageUrl          user image url
     * @param userId            userId that will be called for get particular user details from database
     * @param friendId          friend id who send this message or to whom message sent by this user
     * @param messageType       messageType will be "ME" for user self and otherwise friend, for differentiation
     */

	public void insertChatDetails() {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}
		if (DB != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("bhavesh", "this is a simple name");
			DB.insert("BHAVESH", null, contentValues);
		}

	}
}

