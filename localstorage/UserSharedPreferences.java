package com.wink.localstorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import com.wink.model.User;
import com.wink.model.UserType;
import com.wink.model.Venue;
import com.wink.utils.AppConstant;
import com.wink.utils.StringUtils;

import java.io.UnsupportedEncodingException;

public class UserSharedPreferences {

	private String TAG = UserSharedPreferences.class.getSimpleName();
	
	private final String FILE_NAME = "ApplicationName-Preferences";

	private static UserSharedPreferences mUserSharedPreferences;
	public SharedPreferences preferences;

	public static String KEY_IS_REMEMBER_ME = "is_remember_me";
	public static String KEY_DEVICE_ID = "device_id";
	public static String KEY_USER_DETAILS = "user_details";
	public static String KEY_USER_ID = "user_id";
	public static String KEY_USER_NAME = "user_name";
	public static String KEY_USER_PASSWORD = "password";
    public static String KEY_USER_EMAIL = "user_email";
    public static String KEY_ACCESS_TOKEN = "access_token";
	public static String KEY_USER_TYPE = "user_type";
	public static String KEY_USER_IMAGE = "user_image_url";

	public static String KEY_CHECKEDIN_VENUE_ID = "checkedin_venue_id";
	public static String KEY_CHECKEDIN_VENUE_NAME = "checkedin_venue_name";
	public static String KEY_CHECKEDIN_VENUE_ADDRESS = "checkedin_venue_address";
	public static String KEY_CHECKEDIN_VENUE_CITY = "checkedin_venue_city";
	public static String KEY_CHECKEDIN_VENUE_STATE = "checkedin_venue_state";
	public static String KEY_CHECKEDIN_VENUE_COUNTRY = "checkedin_venue_country";
	public static String KEY_CHECKEDIN_VENUE_ZIP = "checkedin_venue_zip";
	public static String KEY_CHECKEDIN_VENUE_LAT = "checkedin_venue_lat";
	public static String KEY_CHECKEDIN_VENUE_LNG = "checkedin_venue_lng";
	public static String  FIRST_TIME_VIDEO = "first_time_video";

    public static String KEY_IS_ALLOW_FBSHARE_CHECKIN ="is_fbshare_checkin";
    public static String KEY_IS_ALLOW_WINK_PUSH="is_wink_push";

	private UserSharedPreferences() {

	}

	public static UserSharedPreferences getInstance(Context context) {
		if (mUserSharedPreferences == null) {
			mUserSharedPreferences = new UserSharedPreferences();
		}
		mUserSharedPreferences.getPreferenceObject(context);

		return mUserSharedPreferences;
	}

	private void getPreferenceObject(Context context) {
		preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
	}

	public void putString(String key, String value) {
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void putBoolean(String key, Boolean value) {
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void putLong(String key, Long value) {
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public String getString(String key) {
		return preferences.getString(key, "");
	}

	public Boolean getBoolean(String key) {
		return preferences.getBoolean(key, false);
	}

	public int getInt(String key) {
		return preferences.getInt(key, 0);
	}

	public long getLong(String key) {
		return preferences.getLong(key, 0);
	}

	// this method should not be used as we store different type of data for
	// different purposes
	/*
	 * public void cleanAllPreferences() { Editor editor = preferences.edit();
	 * editor.clear(); editor.commit(); }
	 */

	public void persistUserCredentails(String email, String password,String accessToken) {

		putString(UserSharedPreferences.KEY_USER_EMAIL, email);
		putString(UserSharedPreferences.KEY_USER_PASSWORD,
				Base64.encodeToString(password.getBytes(), Base64.DEFAULT));
        putString(AppConstant.ACCESSTOKEN, accessToken);

        // commented according to new api and updates
//		putBoolean(UserSharedPreferences.KEY_IS_REMEMBER_ME, isRememberMe);
	}

	public void persistUser(User user) {
		putString(UserSharedPreferences.KEY_USER_ID, user.getId());
		putString(UserSharedPreferences.KEY_USER_NAME, user.name);
		putString(UserSharedPreferences.KEY_USER_EMAIL, user.email);
		putString(UserSharedPreferences.KEY_USER_TYPE, user.userType);
		putString(UserSharedPreferences.KEY_USER_IMAGE, user.imageUrl);
	}

	public User getUser() {
		User user = new User();
		user.setId(preferences.getString(UserSharedPreferences.KEY_USER_ID, ""));
		user.name = preferences.getString(UserSharedPreferences.KEY_USER_NAME,
				"");
		user.password = preferences.getString(
				UserSharedPreferences.KEY_USER_PASSWORD, "");
		user.email = preferences.getString(
				UserSharedPreferences.KEY_USER_EMAIL, "");
		user.userType = preferences.getString(
				UserSharedPreferences.KEY_USER_TYPE, "");
		user.type = UserType.fromString(preferences.getString(
				UserSharedPreferences.KEY_USER_TYPE, ""));
		
		user.imageUrl = preferences.getString(
				UserSharedPreferences.KEY_USER_IMAGE, "");
		
		return user;
	}

    public String getUserEmail() {
        return preferences.getString(UserSharedPreferences.KEY_USER_EMAIL, "");
    }

    public String getUserAccessToken() {
        return preferences.getString(AppConstant.ACCESSTOKEN, "");
    }

	public UserType getUserType() {
		return UserType.fromString(preferences.getString(
				UserSharedPreferences.KEY_USER_TYPE, ""));
	}

	public String getUserId() {
		return preferences.getString(UserSharedPreferences.KEY_USER_ID, "");
	}

	public String getUserPassword() {

        String password=preferences.getString(
                UserSharedPreferences.KEY_USER_PASSWORD, "");
        if (StringUtils.isBlank(password)){
            return password;
        }else{
            byte[] data1 = Base64.decode(password, Base64.DEFAULT);
            String decodedPW = null;

            try {
                decodedPW = new String(data1, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }
                return decodedPW;
            }
	}

    public boolean isRememberMe() {
        return preferences.getBoolean(UserSharedPreferences.KEY_IS_REMEMBER_ME,
                false);
    }

    public boolean isAllowFBCheckin() {
        return preferences.getBoolean(UserSharedPreferences.KEY_IS_ALLOW_FBSHARE_CHECKIN,
                false);
    }

    public boolean isAllowWinkPush() {
        return preferences.getBoolean(UserSharedPreferences.KEY_IS_ALLOW_WINK_PUSH,
                true);
    }


    public boolean isUserCheckedIn() {
		return StringUtils.isNotBlank(preferences.getString(
				KEY_CHECKEDIN_VENUE_ID, ""));
	}

	public void persistCheckedInVenue(Venue venue) {
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_ID, venue.id);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_NAME, venue.name);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_ADDRESS,
				venue.address);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_CITY, venue.city);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_STATE, venue.state);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_COUNTRY,
				venue.country);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_ZIP, venue.zip);
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_LAT,
				String.valueOf(venue.latitude));
		putString(UserSharedPreferences.KEY_CHECKEDIN_VENUE_LNG,
				String.valueOf(venue.longitude));
	}

	public Venue getCheckedInVenue() {

		Venue venue = new Venue();
		venue.id = preferences.getString(KEY_CHECKEDIN_VENUE_ID, "");
		venue.name = preferences.getString(KEY_CHECKEDIN_VENUE_NAME, "");
		venue.address = preferences.getString(KEY_CHECKEDIN_VENUE_ADDRESS, "");
		venue.city = preferences.getString(KEY_CHECKEDIN_VENUE_CITY, "");
		venue.state = preferences.getString(KEY_CHECKEDIN_VENUE_STATE, "");
		venue.country = preferences.getString(KEY_CHECKEDIN_VENUE_COUNTRY, "");
		venue.zip = preferences.getString(KEY_CHECKEDIN_VENUE_ZIP, "");
		venue.latitude = Double.valueOf(preferences.getString(
				KEY_CHECKEDIN_VENUE_LAT, "0"));
		venue.longitude = Double.valueOf(preferences.getString(
				KEY_CHECKEDIN_VENUE_LNG, "0"));
		return venue;
	}

	public void removeCheckedInVenue() {

		Editor editor = preferences.edit();
		editor.remove(KEY_CHECKEDIN_VENUE_ID);
		editor.remove(KEY_CHECKEDIN_VENUE_NAME);
		editor.remove(KEY_CHECKEDIN_VENUE_ADDRESS);
		editor.remove(KEY_CHECKEDIN_VENUE_CITY);
		editor.remove(KEY_CHECKEDIN_VENUE_STATE);
		editor.remove(KEY_CHECKEDIN_VENUE_COUNTRY);
		editor.remove(KEY_CHECKEDIN_VENUE_ZIP);
		editor.remove(KEY_CHECKEDIN_VENUE_LAT);
		editor.remove(KEY_CHECKEDIN_VENUE_LNG);
		editor.commit();

	}

    public void clearUserData(){
        preferences.edit().clear().commit();
    }
}
