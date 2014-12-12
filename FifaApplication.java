

public class FifaApplication extends Application {

	private static Context context;

	private List<GraphUser> selectedUsers;

	private BitmapLruCache mCache;
	
    public static GoogleAnalyticsTrackerManager TRACKER_MANAGER;
	
	public List<GraphUser> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<GraphUser> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public void onCreate() {
		super.onCreate();

		// DeployGate.install(this);

		FifaApplication.context = getApplicationContext();
		CustomViewConstants.loadFonts(context);
		configImageLoader();

        // init google analytics to track the app
        TRACKER_MANAGER = new GoogleAnalyticsTrackerManager(this);
		// gps = GPSTracker
		// .getInstance(WinkApplication.getAppContext());

	}

	 private void configBitmapCaching() {
			File cacheLocation;

			// If we have external storage use it for the disk cache. Otherwise we
			// use
			// the cache dir
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				cacheLocation = new File(Environment.getExternalStorageDirectory()
						+ "/fifa");
			} else {
				cacheLocation = new File(getFilesDir() + "/fifa");
			}
			cacheLocation.mkdirs();

			BitmapLruCache.Builder builder = new BitmapLruCache.Builder();
			builder.setMemoryCacheEnabled(true)
					.setMemoryCacheMaxSizeUsingHeapSize();
			builder.setDiskCacheEnabled(true).setDiskCacheLocation(cacheLocation);

			mCache = builder.build();

		}

		public static Context getAppContext() {
			return FifaApplication.context;
		}

		public BitmapLruCache getBitmapCache() {
			return mCache;
		}

		public static FifaApplication getApplication(Context context) {
			return (FifaApplication) context.getApplicationContext();
		}


	/**
	 * This is basic configuration for universal image downloader
	 */
	private void configImageLoader() {

		// Create global configuration and initialize ImageLoader with this
		// configuration
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				getDisplayImageOptions()) // default
				.build();

		// ImageLoader.getInstance().han
		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions getDisplayImageOptions() {
		DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().build();

		return displayOptions;
	}

	/**
	 * Set this options while loading user image.
	 * 
	 * @return
	 */
	public static DisplayImageOptions getDisplayImageOptionsWithNewsImage() {
		DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().showStubImage(R.drawable.ic_action_picture)
				.showImageForEmptyUri(R.drawable.ic_action_picture)
				.showImageOnFail(R.drawable.ic_action_picture).build();

		return displayOptions;
	}

}
