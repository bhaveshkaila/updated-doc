
public interface DBConstants {

	String TABLE_MESSAGES = "Messages";
	String TABLE_USER = "User";

	interface WinkDB {

		String KEY_MESSAGEID = "MessageId";
		String KEY_MESSAGEJSONSTRING = "MessageJsonString";
		String KEY_MESSAGESTSTUS = "MessageStatus";
		String KEY_MESSAGEDATETIME = "MessageDateTime";
		String KEY_MESSAGETEXT = "MessageText";
		String KEY_IMAGEURL = "ImageUrl";
		String KEY_GROUPID = "GroupId";
		String KEY_USERID = "userId";
		String KEY_FRIENDID = "friendId";
		String KEY_MESSAGETYPE = "MessageType";
	}

	interface User {
		String KEY_USER_ID = "UserId";
		String KEY_USER_IMAGEURL = "UserImageUrl";
		String KEY_USER_NAME = "UserName";
	}
}
