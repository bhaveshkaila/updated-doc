

public class ValidationUtils {
	/**
	 * method to check whether email address valid or not
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailValid(String email) {
		String reg = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
		if (email == null || !email.matches(reg)) {
			return false;
		}
		return true;
	}

	public static boolean isPhNumberValid(String phNumberStr) {
		if (phNumberStr.length() < 10) {
			return false;
		}
		return true;
	}

	public static boolean isZipValid(String zipStr) {
		if (zipStr.length() < 5) {
			return false;
		}
		return true;
	}

	public static boolean isValidLatLong(Double lat, Double lng) {

		if (lat == null || lat == 0 || lng == null || lng == 0) {
			return false;
		}
		return true;

	}
	
	public static boolean isSuccessResponse(BaseResponse response){
		if( response.status  != null && response.status == HttpConstants.RESPONSE_CODE.RESPONSE_SUCCESS){
			return true;
		}
		
		return false;
	}
	public static boolean isFailResponse(BaseResponse response){
		if(response.status  != null && response.status == HttpConstants.RESPONSE_CODE.RESPONSE_FAIL){
			return true;
		}
		
		return false;
	}
	
	public static boolean isValidURL(String url){
		boolean isValid=false;
		
		if(StringUtils.isNotBlank(url) && (url.startsWith("http://") || url.startsWith("https://"))){
			return true;
		}
		
		return isValid;
	}
	
	public static boolean isValidFacebookURL(String url){
		boolean isValid=false;
		
		if(isValidURL(url) && (url.contains("facebook.com/")||url.contains("FACEBOOK.COM/"))){
			return true;
		}
		
		return isValid;
	}
	
	public static boolean isValidLinkedInURL(String url){
		boolean isValid=false;
		
		if(isValidURL(url) && url.contains("linkedin.com/")){
			return true;
		}
		
		return isValid;
	}
	public static boolean isValidTwitterURL(String url){
		boolean isValid=false;
		
		if(isValidURL(url) && url.contains("twitter.com/")){
			return true;
		}
		
		return isValid;
	}
	public static boolean isValidInstagramURL(String url){
		boolean isValid=false;
		
		if(isValidURL(url) && url.contains("instagram.com/")){
			return true;
		}
		
		return isValid;
	}
}
