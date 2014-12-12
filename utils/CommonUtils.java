

public class CommonUtils {

	// give your server registration url here
	// public static final String SERVER_URL = HttpConstants.DATA.HOST_URL +
	// "/users/rest_registerDevice";

	// Google project id
	public static final String SENDER_ID = "667441527493";

	/**
	 * Tag used on log messages.
	 */
	public static final String TAG = "AndroidHive GCM";

	public static final String PUSH_NOTIFICATION_ACTION = "com.wink.PUSH_NOTIFICATION";

	public static final String EXTRA_MESSAGE = "message";

	public static double currentLat = 0;
	public static double currentLng = 0;

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

	/**
	 * Check whether internet is available or not
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {

		boolean isNetworkAvailable = false;
		if (context != null) {
			ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = conMgr.getActiveNetworkInfo();

			if (info != null && info.isConnected()) {
				isNetworkAvailable = true;
				// Log.e("NetworkInfo","Connected State");
			} else {
				isNetworkAvailable = false;
				/*AlertDialogUtils.getAlertDialog(context, context.getString(R.string.alert_network_not_availabe)).show();*/
                CustomAlertDialog.getAlertDialog(context, context.getString(R.string.alert_network_not_availabe)).show();
			}

		}

		return isNetworkAvailable;
	}

	public static String getExtraString(Intent intent, String extraKey) {
		String value = "";
		if (intent == null)
			throw new RuntimeException("Intent should not be null");

		if (intent.hasExtra(extraKey)) {
			value = intent.getExtras().getString(extraKey);
		}
		return value;
	}

	public static boolean getExtraBoolean(Intent intent, String extraKey) {
		boolean value = false;
		if (intent == null)
			throw new RuntimeException("Intent should not be null");

		if (intent.hasExtra(extraKey)) {
			value = intent.getExtras().getBoolean(extraKey);
		}
		return value;
	}

	public static int convertToDp(Context context, int pixelValue) {
		// Get the screen's density scale
		final float scale = context.getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixelValue * scale + 0.5f);
	}

	public static void syso(String message) {
		System.out.println(message);
	}

	public static void selectItemFromOptionDialog(Context context, int title, final List<String> items,
			final CustomTextView view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		String[] array = items.toArray(new String[items.size()]);
		builder.setItems(array, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				view.setText(items.get(which));
			}
		});
		builder.create();
		builder.show();
	}

	public static void openUrlInBrowser(Context context, String url) {
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(browserIntent);
		} catch (Exception e) {

			/*AlertDialogUtils.getAlertDialog(context, context.getString(R.string.alert_user_profile_link_not_found))
					.show();*/
            CustomAlertDialog.getAlertDialog(context, context.getString(R.string.alert_user_profile_link_not_found))
                    .show();
			Log.d(TAG, e.toString());
			e.printStackTrace();
		}
	}

	public static void setSocialMediaLink(final Context context, ImageView imageView, final String url) {
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openUrlInBrowser(context, url);
			}
		});
	}

	public static Bitmap decodeFile(String path, int width, int height) {

		File f = new File(path);

		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			int REQUIRED_SIZE = 500;
			if (width == 0 || height == 0) {
				REQUIRED_SIZE = 800;
			} else {
				REQUIRED_SIZE = width > height ? (width) : (height);
			}

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp < REQUIRED_SIZE || height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap selectedPicture = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			final int rotation = getImageOrientation(path);
			selectedPicture = CommonUtils.adjustPhotoImage(selectedPicture, rotation, selectedPicture.getWidth(),
					selectedPicture.getHeight());
			Log.d("tag", "scaled bitmap............" + selectedPicture.getHeight());
			return selectedPicture;
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static Bitmap adjustPhotoImage(Bitmap photo, int rotation, int width, int height) {
		final Matrix matrix = new Matrix();
		final RectF imgRect = new RectF(0, 0, photo.getWidth(), photo.getHeight());
		final RectF destRect = new RectF(0, 0, width, height);

		final RectF scaleRect;
		if (rotation == 90 || rotation == 270) {
			scaleRect = new RectF(0, 0, height, width); // swap dimensions
		} else {
			scaleRect = new RectF(destRect);
		}

		matrix.setRectToRect(imgRect, scaleRect, Matrix.ScaleToFit.CENTER);
		matrix.postRotate(rotation, photo.getWidth() / 2, photo.getHeight() / 2);
		return Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
	}

	public static int getImageOrientation(String imagePath) {
		try {
			final ExifInterface exif = new ExifInterface(imagePath);
			final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					return 90;
				case ExifInterface.ORIENTATION_ROTATE_180:
					return 180;
				case ExifInterface.ORIENTATION_ROTATE_270:
					return 270;
				default:
					return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static Bitmap resizeBitMapImage(String filePath, int targetWidth, int targetHeight) {
		Bitmap bitMapImage = null;
		// First, get the dimensions of the image
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		double sampleSize = 0;
		// Only scale if we need to
		// (16384 buffer for img processing)
		Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth - targetWidth);

		if (options.outHeight * options.outWidth * 2 >= 1638) {
			// Load, scaling to smallest power of 2 that'll get it <= desired
			// dimensions
			sampleSize = scaleByHeight ? options.outHeight / targetHeight : options.outWidth / targetWidth;
			sampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));
		}

		// Do the actual decoding
		options.inJustDecodeBounds = false;
		options.inTempStorage = new byte[128];
		while (true) {
			try {
				options.inSampleSize = (int) sampleSize;
				bitMapImage = BitmapFactory.decodeFile(filePath, options);

				break;
			} catch (Exception ex) {
				try {
					sampleSize = sampleSize * 2;
				} catch (Exception ex1) {

				}
			}
		}

		return bitMapImage;
	}

	public static long getWinkFriendRemainingTime(Context context, long seconds) {
		// time difference between device and server from home
		long timeDifference = UserSharedPreferences.getInstance(context).getLong(
				AppConstant.HOME.SERVER_CLIENT_TIME_DIFFERENCE_IN_SEC);

		// current time synced with server time
		long currentTimeInSec = (System.currentTimeMillis() / 1000) - timeDifference;

		// to get friendship end time
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(seconds * 1000);
		cal.add(Calendar.DATE, 1);
		long friendshipEndTimeInSec = cal.getTimeInMillis() / 1000;

		// the remaining secs to end friendship
		long remainingSecs = (friendshipEndTimeInSec - currentTimeInSec);
		return remainingSecs;

	}

	public static long getCurrentMilliesSyncWithServer(Context context, long millies) {
		// time difference between device and server from home
		long timeDifference = UserSharedPreferences.getInstance(context).getLong(
				AppConstant.HOME.SERVER_CLIENT_TIME_DIFFERENCE_IN_SEC);

		// current time synced with server time
		long currentTimeInMillies = ((millies / 1000) - timeDifference) * 1000;

		return currentTimeInMillies;
	}

	public static String getTimeInHHMM(long remainingSecs) {
		long hours = remainingSecs / 3600;
		long minutes = (remainingSecs % 3600) / 60;
		// seconds = remainingSecs % 60;

		return twoDigitString(hours) + ":" + twoDigitString(minutes);
	}

	public static String twoDigitString(long number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public static float distanceFrom(Context context, double lat2, double lng2) {

		double earthRadius = 3958.75;

		double lat1 = 22.293099266666662/* GPSTracker.getInstance(context).getLatitude() */;
		double lng1 = 73.16633523333333/* GPSTracker.getInstance(context).getLongitude() */;

		double dLat = Math.toRadians(lat2 - currentLat);
		double dLng = Math.toRadians(lng2 - currentLng);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(currentLat))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		double feetConversion = 16098*3.2808;

		return new Float(dist * feetConversion).floatValue();
	}

	/*
	 * public static void setNumberOfLinesInTextView(final TextView tv, final int numberOfLines) {
	 * ViewTreeObserver vto = tv.getViewTreeObserver(); vto.addOnGlobalLayoutListener(new
	 * OnGlobalLayoutListener() {
	 * 
	 * @Override public void onGlobalLayout() { ViewTreeObserver obs = tv.getViewTreeObserver();
	 * obs.removeGlobalOnLayoutListener(this); if (tv.getLineCount() > numberOfLines) { //
	 * Log.d("","Line["+tv_yourtext.getLineCount()+"]"+tv_yourtext.getText()); int lineEndIndex =
	 * tv.getLayout().getLineEnd( numberOfLines - 1); String text = tv.getText().subSequence(0,
	 * lineEndIndex - 3) + "..."; tv.setText(text); // Log.d("","NewText:"+text); }
	 * 
	 * } }); }
	 */

	/*
	 * public static UserStatus getUserDetails(Context context) {
	 * 
	 * String userDetails = UserSharedPreferences.getInstance(context).getString(
	 * UserSharedPreferences.KEY_USER_DETAILS); LoginResponse loginResponse= new
	 * Gson().fromJson(userDetails,LoginResponse.class); if (loginResponse == null ||
	 * loginResponse.User.user_id == null) { return null; } return loginResponse.User ; }
	 */

	/*
	 * public static void logDeviceScreenSizeAndDensity(Context context) { // Determine screen size
	 * if ((context.getResources().getConfiguration().screenLayout &
	 * Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
	 * Toast.makeText(context, "Large screen", Toast.LENGTH_LONG).show();
	 * 
	 * } else if ((context.getResources().getConfiguration().screenLayout &
	 * Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
	 * Toast.makeText(context, "Normal sized screen", Toast.LENGTH_LONG) .show();
	 * 
	 * } else if ((context.getResources().getConfiguration().screenLayout &
	 * Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
	 * Toast.makeText(context, "Small sized screen", Toast.LENGTH_LONG) .show(); } else {
	 * Toast.makeText(context, "Screen size is neither large, normal or small",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * // Determine density DisplayMetrics metrics = new DisplayMetrics();
	 * 
	 * Activity activity = (Activity) context;
	 * 
	 * activity.getWindowManager().getDefaultDisplay().getMetrics(metrics); int density =
	 * metrics.densityDpi;
	 * 
	 * if (density == DisplayMetrics.DENSITY_HIGH) { Toast.makeText(context,
	 * "DENSITY_HIGH... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show(); } else if
	 * (density == DisplayMetrics.DENSITY_MEDIUM) { Toast.makeText(context,
	 * "DENSITY_MEDIUM... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show(); } else
	 * if (density == DisplayMetrics.DENSITY_LOW) { Toast.makeText(context,
	 * "DENSITY_LOW... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show(); } else {
	 * Toast.makeText( context, "Density is neither HIGH, MEDIUM OR LOW.  Density is " +
	 * String.valueOf(density), Toast.LENGTH_LONG) .show(); } }
	 */
}
