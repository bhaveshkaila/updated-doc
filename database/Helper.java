


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
		return "/data/data/" + "com/wink" + "/databases/" + DB_NAME;
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
		db.execSQL("CREATE TABLE " + DBConstants.TABLE_MESSAGES + " (" + DBConstants.WinkDB.KEY_MESSAGEID
				+ " integer PRIMARY KEY  NOT NULL ," + DBConstants.WinkDB.KEY_MESSAGEJSONSTRING + " text,"
				+ DBConstants.WinkDB.KEY_MESSAGESTSTUS + " text," + DBConstants.WinkDB.KEY_MESSAGEDATETIME + " text,"
				+ DBConstants.WinkDB.KEY_MESSAGETEXT + " text," + DBConstants.WinkDB.KEY_USERID + " text,"
				+ DBConstants.WinkDB.KEY_GROUPID + " text," + DBConstants.WinkDB.KEY_FRIENDID + " text,"
				+ DBConstants.WinkDB.KEY_IMAGEURL + " text," + DBConstants.WinkDB.KEY_MESSAGETYPE + " text)");

		db.execSQL("CREATE TABLE " + DBConstants.TABLE_USER + "(" + DBConstants.User.KEY_USER_ID
				+ " integer PRIMARY KEY  NOT NULL ," + DBConstants.User.KEY_USER_NAME + " text ,"
				+ DBConstants.User.KEY_USER_IMAGEURL + " text)");
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

	public void insertChatDetails(String messageStatus, String messageDateTime, String messageText, String ImageUrl,
			String userId, String friendId, String messageType) {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}
		if (DB != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(DBConstants.WinkDB.KEY_MESSAGESTSTUS, messageStatus);
			contentValues.put(DBConstants.WinkDB.KEY_MESSAGEDATETIME, messageDateTime);
			contentValues.put(DBConstants.WinkDB.KEY_MESSAGETEXT, messageText);
			contentValues.put(DBConstants.WinkDB.KEY_USERID, userId);
			contentValues.put(DBConstants.WinkDB.KEY_FRIENDID, friendId);
			contentValues.put(DBConstants.WinkDB.KEY_IMAGEURL, ImageUrl);
			contentValues.put(DBConstants.WinkDB.KEY_MESSAGETYPE, messageType);
			DB.insert(DBConstants.TABLE_MESSAGES, null, contentValues);
		}

	}

	public void insertUserDetails(String userid, String username, String userImageUrl) {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}
		if (DB != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(DBConstants.User.KEY_USER_ID, userid);
			contentValues.put(DBConstants.User.KEY_USER_NAME, username);
			contentValues.put(DBConstants.User.KEY_USER_IMAGEURL, userImageUrl);

			DB.insert(DBConstants.TABLE_USER, null, contentValues);
		}
	}

    /**
     * call made for get last chat detail with friend at friendId from internal database
     *
     * @param user          user object that contains the user details
     * @param friendId      friendId with whom the last chat conversation will be fetched
     * @return  message     single object of ChatMessage
     * @see ChatMessage
     */

	public ChatMessage getLastChatDetails(User user, String friendId) {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}
		ChatMessage message = null;
		Cursor cursor = DB.rawQuery("select * from " + DBConstants.TABLE_MESSAGES + " WHERE "
				+ DBConstants.WinkDB.KEY_USERID + " = " + user.getId() + " AND " + DBConstants.WinkDB.KEY_FRIENDID
				+ "=" + friendId, null);
		if (cursor != null && cursor.moveToLast()) {

			message = new ChatMessage(Integer.parseInt(cursor.getString(0)), cursor.getString(4), cursor.getString(8),
					cursor.getString(5), cursor.getString(7), cursor.getString(3), cursor.getString(9));

		}
		if (cursor != null) {
			cursor.close();
		}

		return message;
	}

    /**
     * call made for get all chats for this user with provided friendId from internal database
     *
     * @param user          user object that contains the user details
     * @param friendId      friendId with whom the chat conversations will be fetched
     * @return messageList  return the whole conversations array
     * @see ChatMessage
     */

	public List<ChatMessage> getChatDetails(User user, String friendId) {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}

		List<ChatMessage> messageList = new ArrayList<ChatMessage>();
		ChatMessage message = null;
		Cursor cursor = DB.rawQuery("select * from " + DBConstants.TABLE_MESSAGES + " WHERE "
				+ DBConstants.WinkDB.KEY_USERID + " = " + user.getId() + " AND " + DBConstants.WinkDB.KEY_FRIENDID
				+ "=" + friendId, null);
		// Cursor cursor = DB.rawQuery("select * from " + DBConstants.TABLE_MESSAGES, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					message = new ChatMessage(Integer.parseInt(cursor.getString(0)), cursor.getString(4),
							cursor.getString(8), cursor.getString(5), cursor.getString(7), cursor.getString(3),
							cursor.getString(9));
					message.image = cursor.getString(8);
					message.messageType = cursor.getString(9);
					messageList.add(message);
				} while (cursor.moveToNext());
			}
		}
		if (cursor != null) {
			cursor.close();
		}

		return messageList;

	}

	public void updateImageUrl(String userId, String messageId, String imgUrl) {
		if (DB == null) {
			DB = this.getWritableDatabase();
		}
		String[] selectionArgs = new String[2];
		selectionArgs[0] = imgUrl;
		selectionArgs[1] = messageId;

		if (DB != null) {

			ContentValues cv = new ContentValues();
			cv.put(DBConstants.WinkDB.KEY_IMAGEURL, imgUrl);
			int i = DB.update(DBConstants.TABLE_MESSAGES, cv,
					DBConstants.WinkDB.KEY_MESSAGEID + "='" + messageId + "'", null);

		}
	}

    /**
     * delete all conversations older than last 30 days
     *
     * @param milliseconds  milliseconds time of last 30th day
     */
    public void deleteAllOlderThanLast30DaysConversations(long milliseconds){
       // check for DB null then get writable database
        if (DB == null) {
            DB = this.getWritableDatabase();
        }

        DB.rawQuery("delete from "+DBConstants.TABLE_MESSAGES+" WHERE CAST("+DBConstants.WinkDB.KEY_MESSAGEDATETIME+" as long) < "+milliseconds,null);

    }
}
