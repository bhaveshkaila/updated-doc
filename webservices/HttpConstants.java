package com.wink.webservices;

public class HttpConstants {

	public interface DATA {

		String HOST_URL = "http://203.88.143.51:5020/wink1/"; // local server
		// String HOST_URL = "http://172.16.3.57/wink/"; // local server
		// String HOST_URL = "http://203.88.143.51:5020/wink/"; //development server

		String CHAT_SERVER_URL = "http://203.88.143.51:8001"; // local server
		// String CHAT_SERVER_URL = "http://203.88.143.51:8001"; //development server

		String CHAT_HISTORY_URL = "http://203.88.143.51:3000";

		/*
		 * String USER_URL = HOST_URL+"users/";
		 * 
		 * 
		 * }
		 * 
		 * public interface METHOD { String LOGIN = "rest_login"; String FORGOT_PW_UL =
		 * "rest_forgotPassword";
		 * 
		 * }
		 */
	}

	public interface RESPONSE_CODE {
		public final int RESPONSE_SUCCESS = 0;
		public final int RESPONSE_FAIL = 1;
		public final int RESPONSE_ERROR = -1;
		public final int RESPONSE_FORGOT_PASSWORD = 0x1001;

	}

	public interface RESPONSE_MESSAGE {
		public final String RESPONSE_USER_NOT_ACTIVE = "User is not activated";
	}
}
