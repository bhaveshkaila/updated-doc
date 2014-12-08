package com.fifa.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



@SuppressLint("SimpleDateFormat")
public class CommonUtils {
	public static final String SENDER_ID = "63004385043";
	public static final String PUSH_NOTIFICATION_ACTION = "com.assignit.android.PUSH_NOTIFICATION";

	public static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the UI and the
	 * background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(PUSH_NOTIFICATION_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static void showSoftKeyboard(Activity context, EditText editText) {
		InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (m != null) {
			// m.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
			m.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		}
	}

	public static void hideSoftKeyboard(Activity context) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager != null)
			inputManager.hideSoftInputFromWindow(context.getWindow().getDecorView().getApplicationWindowToken(), 0);
		context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	public static boolean isNetworkAvailable(Context context) {
		boolean isNetworkAvailable = false;
		if (context != null) {
			ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = conMgr.getActiveNetworkInfo();

			if (info != null && info.isConnected()) {
				isNetworkAvailable = true;
			} else {
				isNetworkAvailable = false;
			}
			return isNetworkAvailable;
		} else
			return isNetworkAvailable;
	}

	public static int convertToDp(Context context, int pixelValue) {
		// Get the screen's density scale
		final float scale = context.getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixelValue * scale + 0.5f);
	}

	public static String getFormateDate(String seconds) {

		String result = "0";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		try {
			long secs = Long.valueOf(seconds);
			long millis = secs;

			Date resultdate = new Date(millis);
			result = sdf.format(resultdate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date getExpireFormateDate(String seconds) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date today = null;
		try {
			today=sdf.parse(seconds);
			return today;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return today;

	}
	public static String getFormateDateTime(String seconds) {

		String result = "0";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");

		try {
			long secs = Long.valueOf(seconds);
			long millis = secs;

			Date resultdate = new Date(millis);
			result = sdf.format(resultdate.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String getHours(String secoond){
		Date date = new Date(Long.parseLong(secoond));   // given date
		Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		return String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
	}
	public static String getMinitus(String secoond){
		Date date = new Date(Long.parseLong(secoond));   // given date
		Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		return String.valueOf(calendar.get(calendar.MINUTE));
	}
}
