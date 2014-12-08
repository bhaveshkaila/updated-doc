package com.wink.utils;

public class AppConstant {

	
	public static final int MAX_IMAGE_UPLOAD_SIZE = 1024 * 1024;

    public interface USER_TYPE {
		public final String USER_TYPE_SELECTED = "user_type_selected";
	}

    public static final String ACCESSTOKEN = "access_token";
    public static final String USERID = "user_id";
    public static String FBACCESSTOKEN="fb_access_token";

    public static String ISMYPROFILE="is_my_profile";

    public interface LOGOUT_USER{
        public final String REGISTRATOIN_ID="reg_id";
    }

	public interface LOGIN_SCREEN {
		public final String USER_ID = "user_id";
		public final String USER_NAME = "username";
        public final String PASSWORD = "password";
		public final String STORED_USER_NAME = "stored_user_name";
		public final String STORED_PASSWORD = "stored_password";
	//	public final String IGRONE_AUTO_LOGIN = "irgore_auto_login";
		public final String PLATFORM_PARAMETER = "platform";
		public final String PLATFORM_NAME_ANDORID = "android";

	}

	public interface USER_REGISTRATION {
        public final String NAME = "name";
        public final String LAST_NAME = "last_name";
		public final String EMAIL = "username";
		public final String PASSWORD = "password";
		public final String CITY = "user_city";
		public final String GENDER = "gender";
		public final String PHOTO = "photo";
		public final String FACEBOOK = "facebook_url";
		public final String TWITTER = "twitter_url";
		public final String LINKEDIN = "linkedin_url";
		public final String INSTAGRAM = "instagram";
		public final String USER_TYPE = "usertype_id";
		public final String VENUE_NAME = "venue_name";
		public final String VENUE_ADDRESS = "venue_address";
		public final String VENUE_CITY = "venue_city";
		public final String VENUE_ZIP = "venue_zip";
		public final String VENUE_LAT = "latitude";
		public final String VENUE_LONG = "longitude";

	}
	public interface REFRESH_DELAY_TIME{
		public final int DELAY_TIME = 1000 *60 ;
		
	}
	public interface UPDATE_USER {
		public final String USER_ID = "user_id";
		public final String NAME = "name";
        public final String EMAIL = "username";
		public final String PASSWORD = "password";
		public final String CITY = "user_city";
        public final String GENDER = "gender";
        public final String LASTNAME = "last_name";
        public final String HOMETOWN = "home_town";
        public final String AGE = "age";
        public final String INTERESTED_IN = "interested_in";
        public final String WORKS_AT = "work_at";
        public final String STUDY = "study";
        public final String STUDY_STATUS = "study_status";
        public final String STUDY_AT = "study_at";
		public final String PHOTO = "photo";
		public final String FACEBOOK = "facebook_url";
		public final String TWITTER = "twitter_url";
		public final String LINKEDIN = "linkedin_url";
		public final String INSTAGRAM = "instagram";
        public final String ENABLE_PUSHNOTIFICATION="enable_push_notification";
        public final String LIVE_IN="live_in";
    }
	
	public interface HOME{
		public final String DO_LOGGED_IN = "do_logged_in";
		public final String SERVER_CLIENT_TIME_DIFFERENCE_IN_SEC = "server_client_time_difference";
	}
	public interface CHECKIN{
		public final String VENUE_ALREADY_EXIST = "Venue already exists.";
	}
	
	public interface CHECKIN_CHECKOUT{
		public final String USER_ID = "user_id";
		public final String VENUE_ID = "venue_id";
	}

	public interface WINK_REQUEST{
		public final String USER_ID = "user_id";
		public final String FRIEND_ID = "friend_id";
	}
	
	public interface FRIEND_REQUEST{
		public final String USER_ID = "user_id";
		public final String FRIEND_ID = "friend_id";
	}
	
	public interface VENUES {
		public final String CITY_NAME = "city_name";
		public final String LATITUDE = "latitude";
		public final String LONGITUDE = "longitude";
	}
	
	public interface VERIFICATION_SCREEN{
		public final String VERIFICATION_CODE = "code";
		public final String USER_NAME = "username";
	}
	
	public interface VENUE_FEEDS{
		public final String CITY_NAME = "city_name";
		public final String VENUE_ID = "venue_id";
		public final String VENUE_NAME = "venue_name";
	}

	public interface CREATE_VENUE{
		public final String ID = "external_venue_id";
		public final String NAME = "name";
		public final String ADDRESS = "address";
		public final String CITY_NAME = "city_name";
		public final String ZIP = "zip";
		public final String VENUE_LAT = "latitude";
		public final String VENUE_LONG = "longitude";
		public final String USER_ID = "user_id";
	}
	
	public interface POST_NEWS_FEED{
		public final String CITY_NAME = "city_name";
		public final String VENUE_ID = "venue_id";
		public final String VENUE_NAME = "venue_name";
		public final String TITLE = "title";
		public final String CONTENT = "content";
		public final String IMAGE = "image";
		public final String USER_ID = "user_id";
		public final String CITY_FEED_FLAG = "city_feed_flag";
	}
	
	public interface CHECKEDIN_USERS{
		public final String USER_ID = "user_id";
		public final String VENUE_ID = "venue_id";
		public final String FRIEND_STATUS_CODE = "friend_status_code";
		public final String FRIEND_STATUS = "friend_status";

	}
	
	public interface VENUE_GROUPS{
		public final String USER_ID = "user_id";
		public final String VENUE_ID = "venue_id";
	}
	
	public interface CREATE_GROUP{
		public final String USER_ID = "user_id";
		public final String VENUE_ID = "venue_id";
		public final String GROUP_NAME = "group_name";
		public final String IMAGE_PATH = "group_image" ;
	}

	
	public interface JOIN_GROUP{
		public final String OWNER_ID = "user_id";
		public final String GROUP_ID = "group_id";
		public final String USERS_ID = "users_id";
	}

	public interface ADD_TO_GROUP{
		public final String USER_ID = "user_id";
		public final String GROUP_ID = "group_id";
	}


	public interface ACCEPT_GROUP{
		public final String USER_ID = "user_id";
		public final String GROUP_ID = "group_id";
	}
	
	
	public interface GROUP_USERS{
		public final String USER_ID = "user_id";
		public final String GROUP_ID = "group_id";
	}
	
	public interface REGISTER_DEVICE{
		public final String USER_ID = "user_id";
		public final String REG_ID = "reg_id";
		public final String DEVICE_TYPE = "device_type";
		
	}
	
	
	public interface CHAT{

        public final String IS_PUSHNOTIFICATION="is_pushnotification";
        public final String MESSAGE_IMAGE_URL="image_url";

        public final String USER_ID="user_id";
		public final String FRIEND_ID = "friend_id";
        public final String FRIEND_STATUS_CODE="friendstatuscode";
		public final String FRIEND_NAME = "friend_name";
		public final String GROUP_ID = "group_id";
		public final String GROUP_NAME = "group_name";
        public final String IMAGE_FILE="image_file";
        public final String ACCESS_TOKEN="access_token";
        public final String IMAGE_ID="image_id";
        public final String MESSAGE_ID="MessageID";
	    public final String MESSAGE_TIME="";
        public final String MESSAGE_TEXT="";
    }

    public static int PROFILE_PICTURE_SIZE = 200;

    public interface FACEBOOK_URL{
        public static String SEARCH_TYPE_CITY="https://graph.facebook.com/search?type=adcity&limit=10&q=";
        public static String SEARCH_TYPE_WORKPLACE="https://graph.facebook.com/search?type=adworkplace&limit=10&q=";
        public static String SEARCH_TYPE_COLLAGE="https://graph.facebook.com/search?type=adcollege&limit=10&q=";
        public static String SERACH_TYPE_COLLAGE_MAJOR="https://graph.facebook.com/search?type=adcollegemajor&limit=10&q=";
    }
}
