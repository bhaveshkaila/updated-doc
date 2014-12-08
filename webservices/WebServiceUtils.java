

public class WebServiceUtils {

	 public static HttpClient getHttpClient(){
			HttpParams httpParameters = new BasicHttpParams();
		    // Set the timeout in milliseconds until a connection is established.
		    int timeoutConnection = 50000;
		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		    // Set the default socket timeout (SO_TIMEOUT) 
		    // in milliseconds which is the timeout for waiting for data.
		    int timeoutSocket = 50000;
		    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);	    	
		    HttpClient httpclient = new DefaultHttpClient(httpParameters);	        
		    return httpclient;
		 }
	 
	 public static String convertStreamToString(InputStream is) {		 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
}
