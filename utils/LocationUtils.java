
public class LocationUtils {

	
	public static final String TAG = LocationUtils.class.getSimpleName();
	//venue area in meters
	public static final int COMMON_VENUE_AREA_IN_METERS = 1000;
	
	
	public static Address getLocationByVenue(Venue venue) {

		Address address = null;
		try {
			GPSTracker gps = GPSTracker.getInstance(WinkApplication
					.getAppContext());
			//String strVenue = venue.address;
			
			StringBuilder strVenue = new StringBuilder(venue.address);
			if(StringUtils.isNotBlank(venue.city)){
				strVenue.append(", " + venue.city);
			}
			if(StringUtils.isNotBlank(venue.zip)){
				strVenue.append(", " + venue.zip);
			}

			if (StringUtils.isNotBlank(strVenue.toString())) {
				address = gps.getAddressByLocationName(strVenue.toString());
			}
		} catch (IOException e) {
			Log.d(TAG, e.toString());
			e.printStackTrace();
		}
		return address;
	}
	
	/**
	 * This method compares provided venue lat long with current user position
	 * if user is within configured meter(s) area he/she is considered at venue.
	 * @param venue
	 * @return
	 */
	public static boolean isUserAtVenue(Venue venue, Context context) {
		boolean isUserAtVenue = false;
		if (venue == null
				|| !ValidationUtils.isValidLatLong(venue.latitude,
						venue.longitude))
			throw new RuntimeException("Venue is null or lat long is invalid");

		GPSTracker gps = GPSTracker
				.getInstance(WinkApplication.getAppContext());

		// check if GPS enabled
		if (gps.canGetLocation()) {
			LatLng venueLocation = new LatLng(venue.latitude, venue.longitude);
			LatLng userLocation = new LatLng(gps.getLatitude(),
					gps.getLongitude());
			double distnace = getDistanceInMeters(venueLocation,
					userLocation);
			
			Log.d(TAG, " Venue Lat Long :" + venueLocation.toString() );
			Log.d(TAG, " User Lat Long :" + userLocation.toString() );
			
			Log.d(TAG, " Distance in meters :" + distnace);
			
			if(distnace <= COMMON_VENUE_AREA_IN_METERS){
				isUserAtVenue = true;
			}
			
		} else {
			// can't get location, GPS or Network is not enabled, Ask user to
			// enable GPS/network in settings
			gps.showSettingsAlert(context);
		}

		return isUserAtVenue;
	}
	
	/**
	 * Returns distance between lat long is meters
	 * @param latLng1
	 * @param latLng2
	 * @return
	 */

	public static Double getDistanceInMeters(LatLng latLng1, LatLng latLng2) {
		
	//	LatLng latLng = new LatLng(gps.getLatitude(), gps.getLongitude());
		double distance = latLng1.distance(latLng2);
		return distance * 1000;
	}
}
