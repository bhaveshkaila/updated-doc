

@EFragment
public class LoginFragment extends BaseFragment {
    private String TAG = LoginFragment.class.getSimpleName();

    private String mUserId;
    private String mUserName;
    private String mPassword;

    boolean isFBLogin=false;

    private UserSharedPreferences userSharedPreferences;
    private UiLifecycleHelper uiHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//
        Session session = Session.getActiveSession();
        Log.d("tag","sessoin in login screen.."+session);
        if (session == null) {
            Log.d("tag","sessoin is null..");
            if (savedInstanceState != null) {
                session = Session.restoreSession(getActivity(), null, callback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(getActivity());
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(callback));
            }
        }
//        return getContainerView(getActivity(), R.layout.frag_login_screen, TAG);
       /* Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }*/

        return getContainerView(getActivity(), R.layout.user_login, TAG);

    }

    private void onSessionStateChange(Session session, SessionState state, Object o) {
        if (state.isOpened()) {
            Log.d("tag", "Logged in...");
            if (!StringUtils.isBlank(session.getAccessToken())) {
                userSharedPreferences.putString(AppConstant.FBACCESSTOKEN, session.getAccessToken());
                if (!isFBLogin)
                submitAccessTokenToWink(session.getAccessToken());
            }
        } else if (state.isClosed()) {
            Log.d("tag", "Logged out...");
            userSharedPreferences.putString(AppConstant.FBACCESSTOKEN,"");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.d("tag", "callback called...");
            onSessionStateChange(session, state, exception);
        }
    };

    private void fillUserData() {
        Log.d("tag"," current FB tokemnnnnn...."+userSharedPreferences.getString(AppConstant.FBACCESSTOKEN));
        Log.d("tag"," current server tokemnnnnn...."+userSharedPreferences.getUserAccessToken());
        String email = userSharedPreferences.getUserEmail();
        String password = userSharedPreferences.getUserPassword();
//        email="prachi.durge@rishabhsoft.com";
//        email="prachi.durge@gmail.com";
//        email="moti.prajapati@gmail.com";
//       email="darshan.gandhi@rishabhsoft.com";
//        password="123456";
        etUserEmailAddress.setText(email);
        etUserPassword.setText(password);
        if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {
            CustomProgressbar.showProgressBar(getActivity(),false);
            clearFBLogin();
            login(email, password, null);
        }else if(StringUtils.isNotBlank(userSharedPreferences.getString(AppConstant.FBACCESSTOKEN))){
            clearUserLogin();
            submitAccessTokenToWink(userSharedPreferences.getString(AppConstant.FBACCESSTOKEN));
            isFBLogin=true;
        }
    }

    private void clearUserLogin() {
        userSharedPreferences.putString(UserSharedPreferences.KEY_USER_EMAIL,"");
        userSharedPreferences.putString(UserSharedPreferences.KEY_USER_PASSWORD,"");
    }

    private void clearFBLogin() {
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().closeAndClearTokenInformation();
        }
        Session.setActiveSession(null);
    }

    @StringRes
    String strOK;

    @StringRes
    String alertFakAppsPresent;
    @ViewById
    CustomEditText etUserEmailAddress;

    @ViewById
    CustomEditText etUserPassword;

//    @ViewById
//    CustomButton btnLoginClicked;

    boolean isRememberMe;

    @ViewById
    LoginButton btnFacebookLogin;

    public static List ALL_PERMISSION= Arrays.asList("email","basic_info","user_birthday","user_about_me","user_hometown","user_location");//"publish_stream",

    @AfterViews
    public void InitializeIds() {
        showWinkLogo();
        showWinkButton(true);
        showBackButton(false);

        userSharedPreferences = UserSharedPreferences.getInstance(getActivity());
        btnFacebookLogin.setFragment(this);
//        btnFacebookLogin.setPublishPermissions(ALL_PERMISSION);
        btnFacebookLogin.setReadPermissions(ALL_PERMISSION);

        fillUserData();
        /*
         * Intent intent = getActivity().getIntent();
		 * 
		 * if (UserSharedPreferences.getInstance(getActivity()).isRememberMe())
		 * { String username = UserSharedPreferences.getInstance(getActivity())
		 * .getUserEmail(); String password =
		 * UserSharedPreferences.getInstance(getActivity()) .getUserPassword();
		 * 
		 * etUserEmailAddress.setText(username);
		 * etUserPassword.setText(password); cbRememberLogin.setChecked(true);
		 * 
		 * if (CommonUtils.isNetworkAvailable(getActivity())) {
		 * if(!isIgnoreAutoLogin(intent)){
		 * CustomProgressbar.showProgressBar(getActivity(), true);
		 * login(username, password); }
		 * 
		 * }
		 * 
		 * }
		 */

//        btnFacebookLogin.setProperties(loginButtonProperties);

//        try {
//            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
//                    "com.wink",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//        }
    }

    public void checkFakeApp() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            CustomProgressbar.showProgressBar(getActivity(), true);
            checkFakeAppWebService();
        }
    }

    @Background
    void checkFakeAppWebService() {

        FakeLocationResponse response;
        try {
            Vector<NameValuePair> platFormName = new Vector<NameValuePair>();
            platFormName.add(new BasicNameValuePair(AppConstant.LOGIN_SCREEN.PLATFORM_PARAMETER, AppConstant.LOGIN_SCREEN.PLATFORM_NAME_ANDORID));
            response = (FakeLocationResponse) WebServiceManager.getInstance(
                    getActivity()).getFakeApp(platFormName);
            if (response.FakeApps != null) {
                List<AndroidApp> forbiddenPackages = new ArrayList<AndroidApp>();
                List<AndroidApp> fakeAppList = new ArrayList<AndroidApp>();
                forbiddenPackages.addAll(response.FakeApps);

                fakeAppList = DetectFakeLocationAppUtil.hasForbiddenApps(
                        getActivity(), forbiddenPackages);

                Log.d("isFakeAppPresent", !fakeAppList.isEmpty() + "");
                afterFakeListResponse(fakeAppList);
            } else {
                AlertDialogUtils.showAlert(getActivity(), response.message);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @UiThread
    public void afterFakeListResponse(List<AndroidApp> fakeAppList) {

        CustomProgressbar.hideProgressBar();
        if (!fakeAppList.isEmpty()) {
            // String FakeAppName = fakeAppList.

            CustomAlertDialog.getAlertDialog(  getActivity(),
                    getResources().getString(R.string.alert_title),
                    alertFakAppsPresent, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        dialog.dismiss();
                        getActivity().finish();
                    }

                }
            }).show();

//            AlertDialog dialog = AlertDialogUtils.getAlertDialog(
//                    getActivity(),
//                    getResources().getString(R.string.alert_title),
//                    alertFakAppsPresent, new OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog,
//                                    int which) {
//                    if (which == DialogInterface.BUTTON_POSITIVE) {
//                        dialog.dismiss();
//                        getActivity().finish();
//                    }
//
//                }
//            });
//            dialog.show();

        } else {
            startLoginSafely();
        }
    }

    private void startLoginSafely() {

        Intent intent = getActivity().getIntent();

        if (UserSharedPreferences.getInstance(getActivity()).isRememberMe()) {
            String username = UserSharedPreferences.getInstance(getActivity())
                    .getUserEmail();
            String password = UserSharedPreferences.getInstance(getActivity())
                    .getUserPassword();

            etUserEmailAddress.setText(username);
            etUserPassword.setText(password);
//			cbRememberLogin.setChecked(true);
//			isRememberMe = cbRememberLogin.isChecked();
            if (CommonUtils.isNetworkAvailable(getActivity())) {
                //	if (!isIgnoreAutoLogin(intent)) {
                CustomProgressbar.showProgressBar(getActivity(), true);
                login(username, password, null);
                //	}
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (StringUtils.isNotBlank(userSharedPreferences.getUserAccessToken()))
            uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    @Click
    public void txtSignUpHere() {
        Intent intent = new Intent(getActivity(),
                RegistrationActivity_.class);
        startActivity(intent);
    }

    @Click
    public void txtForgotPassword() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity_.class);
        startActivity(intent);
    }

    @Click
    public void onLogin() {
        mUserName = etUserEmailAddress.getText().toString();
        mPassword = etUserPassword.getText().toString();
        if (LoginScreenValidator.validateLoginInput(getActivity(), mUserName,
                mPassword)) {
            if (CommonUtils.isNetworkAvailable(getActivity())) {
                CustomProgressbar.showProgressBar(getActivity(), true);
                login(mUserName, mPassword, null);
            }
        }
    }

    @UiThread
    public void submitAccessTokenToWink(String accessToken) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        login(null, null, accessToken);
    }


    @Background
    public void login(final String uname, final String upassword, String accessToken) {

        LoginResponse response = null;
        if (accessToken != null) {
            Log.d("tag","fb token for login......"+accessToken);
            response = WebServiceManager.getInstance(getActivity())
                    .fbLogin(accessToken);
        } else {
            response = WebServiceManager.getInstance(getActivity())
                    .login(uname, upassword);
        }

        Log.d(TAG, " Status: " + response.status);
        Log.d(TAG, " Status accestoken: " + response.user.accessToken);

        if (ValidationUtils.isSuccessResponse(response)) {
            persistUserCredentials(uname, upassword, response.user.accessToken);
            afterLogin(response);
        } /*else if (ValidationUtils.isFailResponse(response)
                && response.message
						.equalsIgnoreCase(HttpConstants.RESPONSE_MESSAGE.RESPONSE_USER_NOT_ACTIVE)) {
//			persistUserCredentials(uname, upassword, isRememberMe);
			openVerificationScreen();
		} */ else {
            alertFailureResponse(response);
        }

    }

    public void afterLogin(LoginResponse loginResponse) {
//        saveTimeDifference(loginResponse.user.serverTime - (System.currentTimeMillis() / 1000));
        Log.d("tag","curren titme....."+System.currentTimeMillis() / 1000);
        Log.d("tag","server titme....."+loginResponse.user.serverTime);
        saveTimeDifference((System.currentTimeMillis() / 1000-Long.valueOf(loginResponse.user.serverTime)));
        GetUserResponse userResponse = WebServiceManager.getInstance(
                getActivity()).getUserDetials(loginResponse.user.getId(), loginResponse.user.getId());
        if (ValidationUtils.isSuccessResponse(userResponse)) {
            UserSharedPreferences.getInstance(getActivity()).persistUser(
                    userResponse.user);
            openHomeScreen();
        } else {
            alertFailureResponse(userResponse);
        }

    }

    private void saveTimeDifference(long timeDifference) {
        UserSharedPreferences.getInstance(getActivity()).putLong(AppConstant.HOME.SERVER_CLIENT_TIME_DIFFERENCE_IN_SEC, timeDifference);
    }

    @UiThread
    public void afterGetUserDetails(GetUserResponse response) {

        CustomProgressbar.hideProgressBar();
    }

   /* @UiThread
    protected void openVerificationScreen() {
        CustomProgressbar.hideProgressBar();
        Intent intent = new Intent(getActivity(), VerificationActivity_.class);
        // intent.putExtra(AppConstant.LOGIN_SCREEN.USER_NAME, mUserName);
        startActivity(intent);
        getActivity().finish();

    }*/

    @UiThread
    public void openHomeScreen() {
        CustomProgressbar.hideProgressBar();
        Intent intent = new Intent(getActivity(), HomeActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    @UiThread
    public void alertFailureResponse(BaseResponse response) {
        CustomProgressbar.hideProgressBar();
        AlertDialogUtils.alertFailureResponse(getActivity(), response);
    }

    private void persistUserCredentials(String uname, String upassword,
                                        String accessToken) {
        if (!StringUtils.isBlank(uname) && !StringUtils.isBlank(upassword)) {
            UserSharedPreferences.getInstance(this.getActivity())
                    .persistUserCredentails(uname, upassword, accessToken);
        } else {
            UserSharedPreferences.getInstance(this.getActivity())
                    .putString(AppConstant.ACCESSTOKEN, accessToken);
        }
    }

/*	private boolean isIgnoreAutoLogin(Intent intent) {
		boolean ignoreAutoLogin = false;

		if (intent.hasExtra(AppConstant.LOGIN_SCREEN.IGRONE_AUTO_LOGIN)) {
			ignoreAutoLogin = intent.getExtras().getBoolean(
					AppConstant.LOGIN_SCREEN.IGRONE_AUTO_LOGIN);
		}
		return ignoreAutoLogin;
	}*/

	/*
	 * private void rememberUserDetails() {
	 * 
	 * if (cbRememberLogin.isChecked()) { // encoding the password byte[] data =
	 * null; try { data = mPassword.getBytes("UTF-8"); } catch
	 * (UnsupportedEncodingException e1) { e1.printStackTrace(); } String
	 * encodedPassword = Base64 .encodeToString(data, Base64.DEFAULT);
	 * 
	 * UserSharedPreferences.getInstance(getActivity()).putString(
	 * UserSharedPreferences.KEY_USER_NAME, mUserName);
	 * UserSharedPreferences.getInstance(getActivity()).putString(
	 * UserSharedPreferences.KEY_USER_PASSWORD, encodedPassword);
	 * 
	 * } else { // dp not clear all preferences, userId should be maintained
	 * UserSharedPreferences.getInstance(getActivity()).putString(
	 * UserSharedPreferences.KEY_USER_NAME, "");
	 * UserSharedPreferences.getInstance(getActivity()).putString(
	 * UserSharedPreferences.KEY_USER_PASSWORD, ""); } }
	 */

	/*
	 * public Handler mHandler = new Handler() { public void
	 * handleMessage(Message msg) { CustomProgressbar.hideProgressBar(); if
	 * (msg.arg1 == HttpConstants.RESPONSE_CODE.RESPONSE_LOGIN) { LoginResponse
	 * response = (LoginResponse) msg.obj;
	 * 
	 * if (response.user != null) {
	 * 
	 * 
	 * JSONObject params = WebService.Object(response);
	 * UserSharedPreferences.getInstance(getActivity()) .putString(
	 * UserSharedPreferences.KEY_USER_DETAILS, params.toString());
	 * 
	 * 
	 * UserSharedPreferences.getInstance(getActivity())
	 * .putString(UserSharedPreferences.KEY_USER_ID, response.user.id);
	 * openHomeScreen();
	 * 
	 * } else {
	 * 
	 * Toast.makeText(getActivity(), response.message,
	 * Toast.LENGTH_SHORT).show(); }
	 * 
	 * } else if (msg.arg1 == HttpConstants.RESPONSE_CODE.RESPONSE_FAIL) {
	 * 
	 * CustomException exception = (CustomException) msg.obj; Log.e("exception",
	 * exception.toString()); if (exception.mExceptionMessage != null &&
	 * exception.mExceptionMessage.toString() .equalsIgnoreCase(
	 * getActivity().getResources().getString( R.string.txt_inactive_user))) {
	 * 
	 * CustomAlertDialog alert = CustomAlertDialog
	 * .createAlertDialog(getActivity(), null, new OnDismissListener() {
	 * 
	 * @Override public void onDismiss( DialogInterface dialog) {
	 * 
	 * } }, strOK, new OnClickListener() {
	 * 
	 * @Override public void onClick( DialogInterface dialog, int which) { if
	 * (which == DialogInterface.BUTTON_POSITIVE) { dialog.dismiss();
	 * openVerificationScreen(); }
	 * 
	 * } });
	 * 
	 * alert.setMessage(exception.mExceptionMessage.toString()); alert.show(); }
	 * else { new ExceptionsHandler(getActivity(), exception, null); }
	 * 
	 * } }
	 * 
	 * };
	 */
}
