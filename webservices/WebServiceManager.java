
public class WebServiceManager {

	private WebService ws;

	private static WebServiceManager instance;

	Context context;

	private WebServiceManager(Context context) {
		ws = WebService.getInstance(context);
		this.context = context;
	}

	public static WebServiceManager getInstance(Context context) {
		if (instance == null) {
			instance = new WebServiceManager(context);
		}
		return instance;
	}

	/**
	 * Method being called to get user details
	 * 
	 * @param userId
	 * @param currentUserId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public GetUserResponse getUserDetials(String userId, String currentUserId) {

		if (StringUtils.isBlank(userId))
			throw new RuntimeException("user id should not be null");

		String url = "/users/rest_getUser?&user_id=" + userId + "&current_user_id=" + currentUserId + "&access_token="
				+ UserSharedPreferences.getInstance(context).getString(AppConstant.ACCESSTOKEN);
		GetUserResponse response = (GetUserResponse) ws.get(HttpConstants.DATA.HOST_URL + url, GetUserResponse.class);
		return response;

	}

	/**
	 * Method being called to get user details
	 * 
	 * @param userId
	 * @param currentUserId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ChatHistoryResponse getChatHistory(String userId, String friendId, String time) {

		if (StringUtils.isBlank(userId))
			throw new RuntimeException("user id should not be null");

		String url = "/getChatHistory?&userId=" + userId + "&friendId=" + friendId + "&time=" + time
				+ UserSharedPreferences.getInstance(context).getString(AppConstant.ACCESSTOKEN);
		ChatHistoryResponse response = (ChatHistoryResponse) ws.get(HttpConstants.DATA.CHAT_HISTORY_URL + url,
				ChatHistoryResponse.class);
		return response;

	}

	public BaseResponse submitUserSettings(String url, Vector<NameValuePair> userDetails) {
		BaseResponse userSettingsResponse = (BaseResponse) ws.post(url, userDetails, BaseResponse.class);
		Log.d("webServiceManager-Base Response", userSettingsResponse.toString());
		return userSettingsResponse;

	}

	public LoginResponse fbLogin(String fbAccessToken) {
		Vector<NameValuePair> credentials = new Vector<NameValuePair>();

		addStringParam(credentials, AppConstant.FBACCESSTOKEN, fbAccessToken);

		LoginResponse commonResponse = (LoginResponse) ws.post(
				HttpConstants.DATA.HOST_URL + "users/rest_facebookLogin", credentials, LoginResponse.class);

		return commonResponse;
	}

	public LoginResponse login(String username, String password) {
		Vector<NameValuePair> credentials = new Vector<NameValuePair>();
		/*
		 * credentials.add(new BasicNameValuePair( AppConstant.LOGIN_SCREEN.USER_NAME, username));
		 * credentials.add(new BasicNameValuePair( AppConstant.LOGIN_SCREEN.PASSWORD, password));
		 */

		addStringParam(credentials, AppConstant.LOGIN_SCREEN.USER_NAME, username);
		addStringParam(credentials, AppConstant.LOGIN_SCREEN.PASSWORD, password);

		LoginResponse commonResponse = (LoginResponse) ws.post(HttpConstants.DATA.HOST_URL + "users/rest_login",
				credentials, LoginResponse.class);
		return commonResponse;

	}

	public UserActivationResponse activateUser(String username, String activationCode) {
		Vector<NameValuePair> credentials = new Vector<NameValuePair>();
		/*
		 * credentials.add(new BasicNameValuePair( AppConstant.VERIFICATION_SCREEN.USER_NAME,
		 * username)); credentials.add(new BasicNameValuePair(
		 * AppConstant.VERIFICATION_SCREEN.VERIFICATION_CODE, activationCode));
		 */
		addStringParam(credentials, AppConstant.VERIFICATION_SCREEN.USER_NAME, username);
		addStringParam(credentials, AppConstant.VERIFICATION_SCREEN.VERIFICATION_CODE, activationCode);

		UserActivationResponse response = (UserActivationResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "users/rest_verifyCode", credentials, UserActivationResponse.class);
		return response;

	}

	public BaseResponse submitResetPassword(Vector<NameValuePair> emailValuePair) {

		BaseResponse response = (BaseResponse) ws.post(HttpConstants.DATA.HOST_URL + "users/rest_forgotPassword",

		emailValuePair, LoginResponse.class);

		return response;
	}

	/**
	 * The web service call to create venue
	 * 
	 * @param venue
	 * @param isExternal
	 * @return
	 */
	public CreateVenueResponse createVenue(Venue venue, boolean isExternal) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		// in case of external value, address is compulsory so set not available if it is not
		// available.
		if (isExternal) {
			if (StringUtils.isBlank(venue.address)) {
				venue.address = "N.A.";
			}
		}

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.CREATE_VENUE.NAME, venue.name);
		addStringParam(params, AppConstant.CREATE_VENUE.ADDRESS, venue.address);
		addStringParam(params, AppConstant.CREATE_VENUE.CITY_NAME, venue.city);
		// addStringParam(params,AppConstant.CREATE_VENUE.ZIP, venue.zip);/*removed in new api
		// call(Optional)*/
		addStringParam(params, AppConstant.CREATE_VENUE.VENUE_LAT, String.valueOf(venue.latitude));
		addStringParam(params, AppConstant.CREATE_VENUE.VENUE_LONG, String.valueOf(venue.longitude));

		CreateVenueResponse response;
		if (isExternal) {

			addStringParam(params, AppConstant.CREATE_VENUE.ID, venue.id);
			response = (CreateVenueResponse) ws.post(HttpConstants.DATA.HOST_URL + "venues/rest_createVenueExternal",
					params, CreateVenueResponse.class);

		} else {
			addStringParam(params, AppConstant.CREATE_VENUE.USER_ID, venue.userId);
			response = (CreateVenueResponse) ws.post(HttpConstants.DATA.HOST_URL + "venues/rest_createVenue", params,
					CreateVenueResponse.class);

		}

		return response;

	}

	/**
	 * new api call for user to accept their winks and friends request
	 * 
	 * @param user_id
	 * @return GetWinksResponse
	 */

	public GetWinksResponse getWinks(String user_id) {

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();

		addStringParam(pairs, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(pairs, AppConstant.USERID, user_id);

		GetWinksResponse response = (GetWinksResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_getWinks",
				pairs, GetWinksResponse.class);

		return response;
	}

	/**
	 * Method to get venues for city
	 * 
	 * @param city
	 * @param lat
	 * @param lng
	 * @return
	 */
	public GetVenuesResponse getVenues(String city, double lat, double lng) {

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();

		addStringParam(pairs, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(pairs, AppConstant.VENUES.CITY_NAME, city);
		addStringParam(pairs, AppConstant.VENUES.LATITUDE, String.valueOf(lat));
		addStringParam(pairs, AppConstant.VENUES.LONGITUDE, String.valueOf(lng));

		GetVenuesResponse response = (GetVenuesResponse) ws.post(HttpConstants.DATA.HOST_URL + "venues/rest_getVenues",
				pairs, GetVenuesResponse.class);
		return response;

	}

	/**
	 * Method to get venues for city
	 * 
	 * @param news
	 * @param postToCity
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public PostNewsFeedResponse postNewsFeed(News news, boolean postToCity) throws UnsupportedEncodingException {

		Map<String, ContentBody> paramsMap = new HashMap<String, ContentBody>();
		addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.CITY_NAME, news.city);
		addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.TITLE, news.title);
		addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.CONTENT, news.content);
		addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.USER_ID, news.userId);

		if (StringUtils.isNotBlank(news.venueId)) {
			addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.VENUE_ID, news.venueId);
			addStringParam(paramsMap, AppConstant.POST_NEWS_FEED.CITY_FEED_FLAG, String.valueOf(postToCity));
		}

		if (StringUtils.isNotBlank(news.newsImageUrl)) {
			paramsMap.put(AppConstant.POST_NEWS_FEED.IMAGE, new FileBody(new File(news.newsImageUrl)));
		}

		PostNewsFeedResponse response = (PostNewsFeedResponse) ws.postMutipart(HttpConstants.DATA.HOST_URL
				+ "venues/rest_postVenueFeeds", paramsMap, PostNewsFeedResponse.class);
		return response;

	}

	/**
	 * Method being called to get venue or city news
	 * 
	 * @param city
	 * @param venueId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public NewsFeedResponse getNewsFeed(String city, String venueId) {

		if (StringUtils.isBlank(city))
			throw new RuntimeException("City name should not be null");

		String url = "cities/rest_getCityFeeds/?city_name=" + URLEncoder.encode(city) + "&" + AppConstant.ACCESSTOKEN
				+ "=" + UserSharedPreferences.getInstance(context).getUserAccessToken();

		if (StringUtils.isNotBlank(venueId))
			url = url + "&venue_id=" + venueId;

		NewsFeedResponse response = (NewsFeedResponse) ws
				.get(HttpConstants.DATA.HOST_URL + url, NewsFeedResponse.class);
		return response;

	}

	/**
	 * Method being called to get city news
	 * 
	 * @param city
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public NewsFeedResponse getNewsFeed(String city) {
		return getNewsFeed(city, null);
	}

	/**
	 * Method being called when user gets registered
	 * 
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public UserRegistrationResponse registerUser(User user) throws UnsupportedEncodingException {

		Map<String, ContentBody> paramsMap = new HashMap<String, ContentBody>();

		// addStringParam(paramsMap, AppConstant.ACCESSTOKEN,
		// UserSharedPreferences.getInstance(context).getUserAccessToken());

		addStringParam(paramsMap, AppConstant.USER_REGISTRATION.NAME, user.name);
		addStringParam(paramsMap, AppConstant.USER_REGISTRATION.LAST_NAME, user.lastName);
		addStringParam(paramsMap, AppConstant.USER_REGISTRATION.EMAIL, user.email);
		addStringParam(paramsMap, AppConstant.USER_REGISTRATION.PASSWORD, user.password);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.CITY, user.city);
		addStringParam(paramsMap, AppConstant.USER_REGISTRATION.GENDER, user.gender);

		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.USER_TYPE,
		// user.type.getValue());
		//
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.FACEBOOK,
		// user.facebookUrl);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.TWITTER,
		// user.twitterUrl);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.LINKEDIN,
		// user.linkedinUrl);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.INSTAGRAM,
		// user.instagramUrl);
		//
		// if (user.type != null && user.type.isVenueOwner()) {
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.VENUE_NAME,
		// user.venue.name);
		// addStringParam(paramsMap,
		// AppConstant.USER_REGISTRATION.VENUE_ADDRESS,
		// user.venue.address);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.VENUE_CITY,
		// user.venue.city);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.VENUE_ZIP,
		// user.venue.zip);
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.VENUE_LAT,
		// String.valueOf(user.venue.latitude));
		// addStringParam(paramsMap, AppConstant.USER_REGISTRATION.VENUE_LONG,
		// String.valueOf(user.venue.longitude));
		// }

		if (StringUtils.isNotBlank(user.imageUrl)) {
			paramsMap.put(AppConstant.USER_REGISTRATION.PHOTO, new FileBody(new File(user.imageUrl)));
		}

		UserRegistrationResponse response = (UserRegistrationResponse) ws.postMutipart(HttpConstants.DATA.HOST_URL
				+ "users/rest_register", paramsMap, UserRegistrationResponse.class);
		return response;

	}

	/**
	 * Method being called when user gets registered
	 * 
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public UserRegistrationResponse updateUser(User user) throws UnsupportedEncodingException {

		Map<String, ContentBody> paramsMap = new HashMap<String, ContentBody>();

		addStringParam(paramsMap, AppConstant.ACCESSTOKEN,
				UserSharedPreferences.getInstance(context).getString(AppConstant.ACCESSTOKEN));
		addStringParam(paramsMap, AppConstant.UPDATE_USER.USER_ID, user.getId());

		// addStringParam(paramsMap, AppConstant.UPDATE_USER.PASSWORD,
		// user.password);

		paramsMap.put(AppConstant.UPDATE_USER.NAME, new StringBody(user.name));
		paramsMap.put(AppConstant.UPDATE_USER.LASTNAME, new StringBody(user.lastName));
		paramsMap.put(AppConstant.UPDATE_USER.EMAIL, new StringBody(user.email));
		paramsMap.put(AppConstant.UPDATE_USER.GENDER, new StringBody(user.gender));
		paramsMap.put(AppConstant.UPDATE_USER.LIVE_IN, new StringBody(user.liveIn));
		paramsMap.put(AppConstant.UPDATE_USER.HOMETOWN, new StringBody(user.homeTown));
		paramsMap.put(AppConstant.UPDATE_USER.AGE, new StringBody(user.age));
		paramsMap.put(AppConstant.UPDATE_USER.INTERESTED_IN, new StringBody(user.interestedIn));
		paramsMap.put(AppConstant.UPDATE_USER.WORKS_AT, new StringBody(user.workAt));
		paramsMap.put(AppConstant.UPDATE_USER.STUDY_AT, new StringBody(user.studyAt));

		paramsMap.put(AppConstant.UPDATE_USER.STUDY, new StringBody(user.education));
		paramsMap.put(AppConstant.UPDATE_USER.STUDY_STATUS, new StringBody(user.studyStatus));

		paramsMap.put(AppConstant.UPDATE_USER.FACEBOOK, new StringBody(user.facebookUrl));
		paramsMap.put(AppConstant.UPDATE_USER.TWITTER, new StringBody(user.twitterUrl));
		paramsMap.put(AppConstant.UPDATE_USER.LINKEDIN, new StringBody(user.linkedinUrl));
		paramsMap.put(AppConstant.UPDATE_USER.INSTAGRAM, new StringBody(user.instagramUrl));

		paramsMap.put(AppConstant.UPDATE_USER.ENABLE_PUSHNOTIFICATION, new StringBody(UserSharedPreferences
				.getInstance(context).isAllowWinkPush() ? "1" : "0"));

		if (StringUtils.isNotBlank(user.imageUrl)) {
			paramsMap.put(AppConstant.UPDATE_USER.PHOTO, new FileBody(new File(user.imageUrl)));
		}

		UserRegistrationResponse response = (UserRegistrationResponse) ws.postMutipart(HttpConstants.DATA.HOST_URL
				+ "users/rest_updateUser", paramsMap, UserRegistrationResponse.class);
		return response;

	}

	/**
	 * Web service request to get all checked in users
	 * 
	 * @param userId
	 * @param venueId
	 * @return
	 */
	public GetCkeckedInUsersResponse getCheckedInUsers(String userId, String venueId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();
		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.CHECKEDIN_USERS.USER_ID, userId);
		addStringParam(params, AppConstant.CHECKEDIN_USERS.VENUE_ID, venueId);

		GetCkeckedInUsersResponse response = (GetCkeckedInUsersResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "venues/rest_checkedinUsers", params, GetCkeckedInUsersResponse.class);
		return response;

	}

	/**
	 * Web service request to get venue groups
	 * 
	 * @param userId
	 * @param venueId
	 * @return
	 */
	public GetVenueGroupsResponse getVenueGroups(String userId, String venueId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();
		addStringParam(params, AppConstant.VENUE_GROUPS.USER_ID, userId);
		addStringParam(params, AppConstant.VENUE_GROUPS.VENUE_ID, venueId);

		GetVenueGroupsResponse response = (GetVenueGroupsResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "users/rest_getGroups", params, GetVenueGroupsResponse.class);
		return response;

	}

	/**
	 * Web service request to get all checked in users
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public GetGroupUsersResponse getGroupUsers(String userId, String groupId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();
		addStringParam(params, AppConstant.GROUP_USERS.USER_ID, userId);
		addStringParam(params, AppConstant.GROUP_USERS.GROUP_ID, groupId);

		GetGroupUsersResponse response = (GetGroupUsersResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_getGroupMember", params, GetGroupUsersResponse.class);
		return response;

	}

	/**
	 * Web service request to get all checked in users
	 * 
	 * @param userId
	 * @return
	 */
	public GetFriendsResponse getFriends(String userId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();
		params.add(new BasicNameValuePair(AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context)
				.getUserAccessToken()));
		params.add(new BasicNameValuePair(AppConstant.GROUP_USERS.USER_ID, userId));

		GetFriendsResponse response = (GetFriendsResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_friends",
				params, GetFriendsResponse.class);
		return response;

	}

	/**
	 * Web service call to create group
	 * 
	 * @param userId
	 * @param venueId
	 * @param groupName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public CreateGroupResponse createGroup(String userId, String venueId, String groupName, String image)
			throws UnsupportedEncodingException {
		Map<String, ContentBody> paramsMap = new HashMap<String, ContentBody>();

		addStringParam(paramsMap, AppConstant.CREATE_GROUP.USER_ID, userId);
		addStringParam(paramsMap, AppConstant.CREATE_GROUP.VENUE_ID, venueId);
		addStringParam(paramsMap, AppConstant.CREATE_GROUP.GROUP_NAME, groupName);

		// addStringParam(paramsMap, AppConstant.CREATE_GROUP.IMAGE_PATH, image);

		if (StringUtils.isNotBlank(image)) {
			paramsMap.put(AppConstant.CREATE_GROUP.IMAGE_PATH, new FileBody(new File(image)));
		}

		CreateGroupResponse response = (CreateGroupResponse) ws.postMutipart(HttpConstants.DATA.HOST_URL
				+ "/users/rest_createGroups", paramsMap, CreateGroupResponse.class);
		return response;

	}

	/**
	 * Web service call to join a group
	 * 
	 * @param ownerId
	 * @param groupId
	 * @param userIds
	 * @return
	 */
	public JoinGroupResponse joinGroup(String ownerId, String groupId, String userIds) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.JOIN_GROUP.OWNER_ID, ownerId);
		addStringParam(params, AppConstant.JOIN_GROUP.GROUP_ID, groupId);
		addStringParam(params, AppConstant.JOIN_GROUP.USERS_ID, userIds);

		JoinGroupResponse response = (JoinGroupResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_joinGroup",
				params, JoinGroupResponse.class);
		return response;

	}

	/**
	 * Web service call to add user to a group
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public AddToGroupResponse addToGroup(String userId, String groupId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ADD_TO_GROUP.USER_ID, userId);
		addStringParam(params, AppConstant.ADD_TO_GROUP.GROUP_ID, groupId);

		AddToGroupResponse response = (AddToGroupResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_addToGroup", params, AddToGroupResponse.class);
		return response;

	}

	/**
	 * Web service call to checkin in venue
	 * 
	 * @param userId
	 * @param venueId
	 * @return
	 */
	public CheckInCheckOutResponse checkIn(String userId, String venueId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.CHECKIN_CHECKOUT.USER_ID, userId);
		addStringParam(params, AppConstant.CHECKIN_CHECKOUT.VENUE_ID, venueId);

		CheckInCheckOutResponse response = (CheckInCheckOutResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/venues/rest_checkin", params, CheckInCheckOutResponse.class);
		return response;

	}

	/**
	 * Web service call to checkout from a venue
	 * 
	 * @param userId
	 * @param venueId
	 * @return
	 */
	public CheckInCheckOutResponse checkOut(String userId, String venueId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.CHECKIN_CHECKOUT.USER_ID, userId);
		addStringParam(params, AppConstant.CHECKIN_CHECKOUT.VENUE_ID, venueId);

		CheckInCheckOutResponse response = (CheckInCheckOutResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/venues/rest_checkout", params, CheckInCheckOutResponse.class);

		// also removed checkedin info from shared preference, as user has checked out.
		UserSharedPreferences.getInstance(context).removeCheckedInVenue();
		return response;

	}

	/**
	 * Send wink web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public WinkResponse sendWink(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.WINK_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.WINK_REQUEST.FRIEND_ID, friendId);

		WinkResponse response = (WinkResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_sendWink", params,
				WinkResponse.class);
		return response;

	}

	/**
	 * Accept wink web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public WinkResponse acceptWink(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.WINK_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.WINK_REQUEST.FRIEND_ID, friendId);

		WinkResponse response = (WinkResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_acceptWink", params,
				WinkResponse.class);
		return response;

	}

	/**
	 * Accept wink web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public WinkResponse declineWink(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.WINK_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.WINK_REQUEST.FRIEND_ID, friendId);

		WinkResponse response = (WinkResponse) ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_declineWink", params,
				WinkResponse.class);
		return response;

	}

	/**
	 * Send friend request web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public FriendRequestResponse sendFriendRequest(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.FRIEND_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.FRIEND_REQUEST.FRIEND_ID, friendId);

		FriendRequestResponse response = (FriendRequestResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_sendFriendRequest", params, FriendRequestResponse.class);
		return response;

	}

	/**
	 * Accept friend request web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public FriendRequestResponse acceptFriendRequest(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.FRIEND_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.FRIEND_REQUEST.FRIEND_ID, friendId);

		FriendRequestResponse response = (FriendRequestResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_acceptFriendRequest", params, FriendRequestResponse.class);
		return response;

	}

	/**
	 * newly added in new requirement
	 * 
	 * @param userId
	 * @param friendId
	 * @return Friend response
	 */
	public FriendRequestResponse deleteFriend(String userId, String friendId) {

		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
		addStringParam(params, AppConstant.FRIEND_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.FRIEND_REQUEST.FRIEND_ID, friendId);

		FriendRequestResponse response = (FriendRequestResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_deleteFriend", params, FriendRequestResponse.class);
		return response;

	}

	/**
	 * Decline friend request web service call
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public FriendRequestResponse declineFriendRequest(String userId, String friendId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.FRIEND_REQUEST.USER_ID, userId);
		addStringParam(params, AppConstant.FRIEND_REQUEST.FRIEND_ID, friendId);

		FriendRequestResponse response = (FriendRequestResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_declineFriendRequest", params, FriendRequestResponse.class);
		return response;

	}

	/**
	 * Web service call to accept group
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public AcceptGroupResponse acceptGroup(String userId, String groupId) {
		Vector<NameValuePair> params = new Vector<NameValuePair>();

		addStringParam(params, AppConstant.ACCEPT_GROUP.USER_ID, userId);
		addStringParam(params, AppConstant.ACCEPT_GROUP.GROUP_ID, groupId);

		AcceptGroupResponse response = (AcceptGroupResponse) ws.post(HttpConstants.DATA.HOST_URL
				+ "/users/rest_acceptGroup", params, AcceptGroupResponse.class);
		return response;

	}

	/*	*/

	/**
	 * Web service call to accept group
	 * 
	 * @param
	 * @param
	 * @return
	 */
	/*
	 * public AcceptGroupResponse registerDevice(String userId, String regId, String deviceType) {
	 * Vector<NameValuePair> params = new Vector<NameValuePair>();
	 * 
	 * addStringParam(params, AppConstant.REGISTER_DEVICE.USER_ID, userId); addStringParam(params,
	 * AppConstant.REGISTER_DEVICE.REG_ID, regId); addStringParam(params,
	 * AppConstant.REGISTER_DEVICE.DEVICE_TYPE, deviceType);
	 * 
	 * AcceptGroupResponse response = (AcceptGroupResponse) ws.post( HttpConstants.DATA.HOST_URL +
	 * "/users/rest_registerDevice", params, AddToGroupResponse.class); return response;
	 * 
	 * }
	 */
	private void addStringParam(Map<String, ContentBody> paramsMap, String key, String value)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(value)) {
			paramsMap.put(key, new StringBody(value));
		}
	}

	private void addStringParam(List<NameValuePair> params, String key, String value) {
		if (StringUtils.isNotBlank(value)) {
			params.add(new BasicNameValuePair(key, value));
		}
	}

	public BaseResponse getFakeApp(Vector<NameValuePair> platformName) {

		BaseResponse response = (BaseResponse) ws.post(HttpConstants.DATA.HOST_URL + "configurations/rest_getFakeApp",
				platformName, FakeLocationResponse.class);

		return response;
	}

	public FBAutoFillData getFBData(String url) {
		FBAutoFillData res = (FBAutoFillData) ws.get(url, FBAutoFillData.class);
		return res;
	}

	public ChatMessageResponse getChatImageResponse(String userId, String imageUrl, String imageid)
			throws UnsupportedEncodingException {
		Map<String, ContentBody> params = new HashMap<String, ContentBody>();

		if (StringUtils.isNotBlank(imageUrl)) {
			params.put(AppConstant.CHAT.IMAGE_FILE, new FileBody(new File(imageUrl)));
		}
		addStringParam(params, AppConstant.CHAT.USER_ID, userId);
		addStringParam(params, AppConstant.CHAT.ACCESS_TOKEN, UserSharedPreferences.getInstance(context)
				.getUserAccessToken());
		addStringParam(params, AppConstant.CHAT.IMAGE_ID, imageid);

		ChatMessageResponse response = (ChatMessageResponse) ws.postMutipart(HttpConstants.DATA.HOST_URL
				+ "users/rest_uploadImage", params, ChatMessageResponse.class);

		return response;
	}

	public BaseResponse postOnFB() {
		BaseResponse response = ws.get("https://graph.facebook.com/me/feed?access_token="
				+ UserSharedPreferences.getInstance(context).getString(AppConstant.FBACCESSTOKEN) + "&message=hello",
				BaseResponse.class);
		return response;
	}

	public Addresses getGoogleAddress(String url) {
		Addresses address = ws.getAddress(url, Addresses.class);
		return address;
	}

    public BaseResponse logoutUser() {
        Vector<NameValuePair> params = new Vector<NameValuePair>();
        addStringParam(params, AppConstant.ACCESSTOKEN, UserSharedPreferences.getInstance(context).getUserAccessToken());
        addStringParam(params, AppConstant.LOGOUT_USER.REGISTRATOIN_ID, GCMRegistrar.getRegistrationId(context));

        BaseResponse response = ws.post(HttpConstants.DATA.HOST_URL + "/users/rest_unregisterDevice", params,
                BaseResponse.class);

        return response;
    }
}
