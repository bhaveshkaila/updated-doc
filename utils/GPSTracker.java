
public class GPSTracker extends Service implements /* LocationListener, */LocationResult {

	private static final String TAG = GPSTracker.class.getSimpleName();

	public static final String LOCATION_UPDATED = "GPSTracker.LOCATION_UPDATED";
	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	Address address; // current address

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // 2 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	LocalBroadcastManager mLocalBroadcastManager;

	Geocoder gcd;

	GPSLocator gpsLocator;

	private static GPSTracker gpsTracker;

	private GPSTracker(Context context) {
		this.mContext = context;
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);

	}

	@Override
	public void gotLocation(Location location) {

		this.location = location;

		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			updateAddress(latitude, longitude);

			if (UserSharedPreferences.getInstance(mContext).isUserCheckedIn()) {
				Venue checkedInVenue = UserSharedPreferences.getInstance(mContext).getCheckedInVenue();
				if (!LocationUtils.isUserAtVenue(checkedInVenue, mContext)) {
					doCheckout(checkedInVenue);
				}
			}
			this.canGetLocation = true;
		} else {
//			this.canGetLocation = false;
		}
		Log.d("tag", "location.GOT..00..........." + latitude);
		Log.d("tag", "location.GOT..11..........." + longitude);
	}

	public static GPSTracker getInstance(Context context) {
		if (gpsTracker == null) {
			gpsTracker = new GPSTracker(context);
		}
		gpsTracker.getLocation();
		return gpsTracker;
	}

	public Location getLocation() {

		if (gcd == null)
			gcd = new Geocoder(mContext, Locale.getDefault());

		if (gpsLocator == null) {
			gpsLocator = new GPSLocator();
            Log.d("tag", "gps locator created...........");

			if (gpsLocator.getLocation(mContext, this)) {
                Log.d("tag", "gps locator created...........");
				this.canGetLocation = true;
				return location;
			}
        }
		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS() {
		// if (locationManager != null) {
		// locationManager.removeUpdates(GPSTracker.this);
		// }
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public Address getCurrentAddress() {
		if (address == null) {
			updateAddress(getLatitude(), getLongitude());
		}
		return address;
	}

	public Address getAddressByLocationName(String locationName) throws IOException {
		List<Address> address = gcd.getFromLocationName(locationName, 1);
		if (address == null || address.isEmpty())
			return null;
		else
			return address.get(0);
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will lauch Settings
	 * Options
	 * */
	public void showSettingsAlert(final Context context) {
		/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);*/

        CustomAlertDialog.createAlertDialog(context,context.getString(R.string.gps_not_enabled),"Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }
            },"Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
            }).show();

		/*// Setting Dialog Title
		alertDialog.setTitle(context.getString(R.string.alert_title));

		// Setting Dialog Message
		alertDialog.setMessage(context.getString(R.string.gps_not_enabled));

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();*/
	}

	/**
	 * Update location with newly recieved lat long
	 * 
	 * @param lat
	 * @param lng
	 */
	public void updateAddress(double lat, double lng) {

		List<Address> addresses = new ArrayList<Address>();
		int attampt = 0;
		this.address = null;

//		while (attampt < 3 && this.address == null) {
//			try {
//
//				// addresses=coder.getFromLocation(lat,lng,1);
//
//				addresses = gcd.getFromLocation(lat, lng, 1);
//				if (addresses != null && addresses.size() > 0) {
//					// setCurrentCity(addresses.get(0).getLocality());
//					// txtVenueAddress.setText(addresses.get(0).getAddressLine(0));
//					// txtVenueZip.setText(addresses.get(0).getPostalCode());
//					this.address = addresses.get(0);
//					Log.d(TAG, "Attampt: " + attampt);
//					Log.d(TAG, "Current Address: " + this.address.getAddressLine(0));
//					Log.d(TAG, "Current City: " + this.address.getLocality());
//					Log.d(TAG, "Current Postal Code: " + this.address.getPostalCode());
//					break;
//				}
//			} catch (IOException e) {
//				Log.d(TAG, e.toString());
//				// if IO error occures we will ignore it and will try to get it again two more
//				// times.
//				e.printStackTrace();
//			}
//			attampt++;
//			if (attampt == 3) {
                try {
                    address=new GetAddressTask().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    address=null;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    address=null;
                }
//            }
//		}
	}

	public class GetAddressTask extends AsyncTask<Void, Void, Address> {

		@Override
		protected Address doInBackground(Void... voids) {
			return getAddressFromGoogle();
		}
	}

	private Address getAddressFromGoogle() {
		Address address1 = null;
        String url = String
                .format(Locale.ENGLISH,"http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language="
                        + Locale.getDefault().getCountry(), latitude, longitude);

		Addresses googleAddress = WebServiceManager.getInstance(mContext).getGoogleAddress(url);
		if (googleAddress.status.equalsIgnoreCase("ok")) {
            address1 = new Address(Locale.getDefault());
			List<Components> componentsList = googleAddress.results.get(0).componentses;
			for (Components components : componentsList) {
				if (components.types[0].equalsIgnoreCase("locality")) {
					String locality = components.locality;
					if (StringUtils.isNotBlank(locality)) {
						address1.setLocality(locality);
						break;
					}
					continue;
				}
			}
            String formattedAddress=googleAddress.results.get(0).formattedAddress;
            address1.setAddressLine(0, formattedAddress);
            this.address=address1;
		} else {
			Log.d("tag", "address failure..." + googleAddress.status);
            this.address=null;
		}
		return address1;
	}

	public void doCheckout(Venue checkedInVenue) {
		new CheckoutTask().execute(checkedInVenue);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private class CheckoutTask extends AsyncTask<Venue, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Venue... params) {

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// AlertDialogUtils.showAlert(mContext, "You are away");
			Intent broadcastIntent = new Intent(LOCATION_UPDATED);
			broadcastIntent.putExtra(LOCATION_UPDATED, LOCATION_UPDATED);
			mLocalBroadcastManager.sendBroadcast(broadcastIntent);
		}

	}
}