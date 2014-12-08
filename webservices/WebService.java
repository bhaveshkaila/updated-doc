package com.wink.webservices;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.MalformedJsonException;

import com.google.gson.Gson;
import com.wink.R;
import com.wink.activity.LoginActivity_;
import com.wink.localstorage.UserSharedPreferences;
import com.wink.webservices.responsebean.Addresses;
import com.wink.webservices.responsebean.BaseResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class WebService {

    private HttpClient mHttpClient;
    private Gson mGson;
    private Context mContext;
    private String url;

    private final static String LOG_TAG = WebService.class.getSimpleName();

    private WebService(Context context) {
        mGson = new Gson();
        mHttpClient = HttpClientFactory.getThreadSafeClient();
        //mHttpClient = WebServiceUtils.getHttpClient();
        mContext = context;
    }

    public WebService(String string) {
        url = string;
    }

    public static WebService getInstance(Context context) {
        WebService instance = new WebService(context);
        return instance;
    }

    public BaseResponse get(String url, Class<?> clazz) {

        Log.d("URL CALLED", url);

        BaseResponse jsonResponse = null;
        try {
            HttpResponse response = mHttpClient.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            //convertStreamToString(entity.getContent());
            Reader reader = new InputStreamReader(entity.getContent());
            jsonResponse = (BaseResponse) mGson.fromJson(reader, clazz);
            setServerError(jsonResponse);
        } catch (SocketTimeoutException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_on_server_error));
            ste.printStackTrace();
        } catch (SocketException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_on_server_error));
            ste.printStackTrace();
        } catch (UnknownHostException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_on_server_error));
            ste.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG,
                    "While getting server response server generate error. (WebService) ");
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_on_server_error));

        }
        return jsonResponse;
    }

    public synchronized BaseResponse post(String url,
                                          List<NameValuePair> nameValuePairs, Class<?> clazz) {

        Log.d("URL CALLED :", url);
        Log.d("PARAMS :", nameValuePairs.toString());

        BaseResponse jsonResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = mHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            // convertStreamToString(entity.getContent());
            Reader reader = new InputStreamReader(entity.getContent());
            jsonResponse = (BaseResponse) mGson.fromJson(reader, clazz);
            setServerError(jsonResponse);
        } catch (SocketTimeoutException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_socket_time_out));
            ste.printStackTrace();
        } catch (SocketException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_socket_Exception));
            ste.printStackTrace();
        } catch (UnknownHostException ste) {
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_unkown_host));
            ste.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,
                    "While getting server response server generate error. ");
            jsonResponse = getBadResponse(clazz, mContext.getResources()
                    .getString(R.string.alert_on_server_error));
        }

        return jsonResponse;
    }

    public synchronized BaseResponse postMutipart(String url,
                                                  Map<String, ContentBody> paramsMap, Class<?> clazz) {

        Log.d("URL CALLED", url);
        Bitmap bm;
        BaseResponse jsonResponse = null;
        try {

            //	bm = BitmapFactory.decodeFile("/sdcard/DCIM/forest.png");

            // ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // bm.compress(CompressFormat.JPEG, 75, bos);
            // byte[] data = bos.toByteArray();

            HttpPost httpPost = new HttpPost(url);
            //ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (paramsMap != null) {
                Iterator<Entry<String, ContentBody>> iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, ContentBody> entry = iterator.next();
                    reqEntity.addPart(entry.getKey(), entry.getValue());
                }
            }
            //reqEntity.addPart("uploaded", bab);
            //reqEntity.addPart("gender", new StringBody("male"));
            //reqEntity.addPart(new FileB)
            httpPost.setEntity(reqEntity);
            HttpResponse response = mHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //convertStreamToString(entity.getContent());
            Reader reader = new InputStreamReader(entity.getContent());
            jsonResponse = (BaseResponse) mGson.fromJson(reader, clazz);
            setServerError(jsonResponse);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Unable to execute webrequest " + url, e);
            try {
                jsonResponse = (BaseResponse) clazz.newInstance();
                jsonResponse.setErrorInfo(HttpConstants.RESPONSE_CODE.RESPONSE_ERROR, e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return jsonResponse;
    }

    private BaseResponse getBadResponse(Class<?> clazz, String message) {
        BaseResponse jsonResponse = null;
        try {
            jsonResponse = (BaseResponse) clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (jsonResponse != null)
            jsonResponse.message = message;
        jsonResponse.status = HttpConstants.RESPONSE_CODE.RESPONSE_FAIL;
        return jsonResponse;

    }

    private void setServerError(BaseResponse baseResponse) {

        if (baseResponse != null && baseResponse.message != null){
            baseResponse.message = baseResponse.message;
            if (baseResponse.status==401){
               doLoginOnUnAuthorizationExecption();
            }
        }
    }

    private void doLoginOnUnAuthorizationExecption() {
        Intent intent=new Intent(mContext, LoginActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void convertStreamToString(InputStream input) throws IOException {
        InputStream in = input/* your InputStream */;
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while (read != null) {
            // System.out.println(read);
            sb.append(read);
            read = br.readLine();

        }
        System.out.println(sb.toString());
        // return sb.toString();
    }

    public BaseResponse webInvoke(Vector<NameValuePair> credentials) {

        return null;
    }

    public static JSONObject Object(Object o) {
        try {
            return new JSONObject(new Gson().toJson(o));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Addresses getAddress(String url, Class<Addresses> clazz) {
        Addresses jsonResponse=null;
        try {
            HttpResponse response = mHttpClient.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            //convertStreamToString(entity.getContent());
            Reader reader = new InputStreamReader(entity.getContent());
            jsonResponse = mGson.fromJson(reader, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse=new Addresses();
            jsonResponse.status=e.getMessage();
        }
        return jsonResponse;
    }
}