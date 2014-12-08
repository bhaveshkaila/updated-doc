package com.wink.webservices;


public class RemoteMethods {

	/*public static void login(Vector<NameValuePair> credentials,Context context)
			throws ClientProtocolException, IOException {
		
		WebService webService = new WebService(HttpConstants.DATA.HOST_URL+HttpConstants.DATA.USER_URL+HttpConstants.METHOD.LOGIN);
		BaseResponse response = webService.webInvoke(credentials);

		

	//	return loginResponse;

	}*/

	/*public static ForgotPasswordResponse forgotPassword(
			ForgotPasswordRequest forgotPassword)
			throws ClientProtocolException, IOException {
		WebService webService = new WebService(HttpConstants.DATA.URL);

		JSONObject params = WebService.Object(forgotPassword);

		String response = webService.webInvoke(
				HttpConstants.METHOD.FORGOT_PASSWORD, params.toString(),
				HttpConstants.DATA.CONTENT_TYPE);

		ForgotPasswordResponse forgotPasswordResponse = new Gson().fromJson(
				response, ForgotPasswordResponse.class);

		return forgotPasswordResponse;

	}

	public static RegisterResponse register(RegisterRequest register)
			throws ClientProtocolException, IOException {
		WebService webService = new WebService(HttpConstants.DATA.URL);

		JSONObject params = WebService.Object(register);

		String response = webService.webInvoke(HttpConstants.METHOD.REGISTER,
				params.toString(), HttpConstants.DATA.CONTENT_TYPE);

		RegisterResponse forgotPasswordResponse = new Gson().fromJson(response,
				RegisterResponse.class);

		return forgotPasswordResponse;

	}*/

	

}
