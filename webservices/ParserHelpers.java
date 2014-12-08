package com.wink.webservices;

import android.os.Message;

import com.wink.exceptionhandlers.CustomException;
import com.wink.webservices.responsebean.BaseResponse;


public class ParserHelpers {

	public static void updateMessage(Message msg, BaseResponse response , int successResponseCode){
		
		if(response.status == HttpConstants.RESPONSE_CODE.RESPONSE_FAIL){
			CustomException customEx = new CustomException(null, CustomException.TYPE_WEBSERVICE_RESPONSE, response.message);
			msg.arg1 = HttpConstants.RESPONSE_CODE.RESPONSE_FAIL;
			msg.obj = customEx;
		}
		else{
			msg.arg1 = successResponseCode;
			msg.obj = response;
		}
	}
	
	public static void updateMessageForServerError(Message msg, Exception e){
		CustomException customEx = new CustomException(null, CustomException.TYPE_SERVER_ERROR, e.getMessage());
		msg.arg1 = HttpConstants.RESPONSE_CODE.RESPONSE_FAIL;
		msg.obj = customEx;
	}
	
	public static void updateMessageForApplicationError(Message msg){
		
	}
	
}
