package com.golden11.app.volleyWebservice;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.golden11.app.R;
import com.golden11.app.activity.MyAppBaseActivity;
import com.golden11.app.localStorage.PreferencesHelper;
import com.golden11.app.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is webservice common module to send json array or json object request to the class.
 */
public class WebServiceHelper {

    private static final String TAG = WebServiceHelper.class.getName();
    private static WebServiceHelper instance;
    private final RequestQueue mRequestQueue;
    private Gson gson;
    private Context context;
    private PreferencesHelper preferencesHelper;

    public WebServiceHelper(Context context) {
        super();
        gson = new Gson();
        this.context = context;
        preferencesHelper = new PreferencesHelper(context);
        mRequestQueue = Volley.newRequestQueue(context);

    }

    /**
     * get signle instance of the class
     *
     * @param context
     * @return
     */
    public static WebServiceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new WebServiceHelper(context);
        }
        return instance;
    }


    /**
     * @param error                : 401 - System error
     *                             403 - System error
     *                             404 -
     *                             406 - Anonymous user
     * @param onWebServiceListener : OnWebServiceListener
     */
    private void handleErrorResponse(VolleyError error, OnWebServiceListener onWebServiceListener, boolean isToken) {
        try {
            if (isToken && error.networkResponse != null && error.networkResponse.statusCode != 0 &&
                    error.networkResponse.statusCode == 401 || error.networkResponse.statusCode == 403) {

                Utils.clearDataAndLogin(context);
            } else {
                if (error.networkResponse != null && error.networkResponse.data != null && !Utils.isBlank(new String(error.networkResponse.data))) {
                    onWebServiceListener.failureResponse(new String(error.networkResponse.data), error.networkResponse.statusCode);
                } else
                    onWebServiceListener.failureResponse(context.getString(R.string.unable_to_connect_server), 0);
            }
        } catch (Exception e) {
            onWebServiceListener.failureResponse(context.getString(R.string.unable_to_connect_server), 0);
        }


    }

    /**
     * @param error                : 401 - System error
     *                             403 - System error
     *                             404 -
     *                             406 - Anonymous user
     * @param onWebServiceListener : OnArrayWebServiceListener
     */
    private void handleErrorResponse(VolleyError error, OnArrayWebServiceListener onWebServiceListener, boolean isToken) {
        try {
            if (isToken && error.networkResponse != null && error.networkResponse.statusCode != 0 &&
                    error.networkResponse.statusCode == 401 || error.networkResponse.statusCode == 403) {

                Utils.clearDataAndLogin(context);
            } else {
                if (error.networkResponse != null && error.networkResponse.data != null && !Utils.isBlank(new String(error.networkResponse.data))) {
                    onWebServiceListener.failureResponse(new String(error.networkResponse.data), error.networkResponse.statusCode);
                } else
                    onWebServiceListener.failureResponse(context.getString(R.string.unable_to_connect_server), 0);
            }
        } catch (Exception e) {
            onWebServiceListener.failureResponse(context.getString(R.string.unable_to_connect_server), 0);
        }
    }


    /**
     * This method is used to send post request to user and get json object response to user and
     * pass the key values as a request params.
     *
     * @param url                  server url
     * @param rp                   request param
     * @param isToken              request token
     * @param onWebServiceListener response listner
     */

    public void postKeyValueToJsonObject(String url, Map<String, String> rp, final boolean isToken, final OnWebServiceListener onWebServiceListener) {
        // public void setUserLogin() {
        Log.d(TAG, url);
        CustomRequest request = new CustomRequest(context, Request.Method.POST, url, rp, isToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response.toString());
                onWebServiceListener.successResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error : " + error.toString());
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }


    /**
     * This function is used to call json post request and get json object response but need to pass
     * json object as a request param.
     *
     * @param url                  server url
     * @param rp                   json string request
     * @param isToken              is token
     * @param onWebServiceListener listner
     */
    public void postJsonToJsonObject(String url, String rp, final boolean isToken, final OnWebServiceListener onWebServiceListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, rp, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response.toString());
                onWebServiceListener.successResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error : " + error.toString());
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.SESSION_NAME) + "=" + preferencesHelper.getPrefString(PreferencesHelper.SESSION_ID));
                if (isToken) {
                    headers.put("X-CSRF-Token", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                    Log.i("TOKEN::", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                }
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }


    /**
     * This function is used to call json put request and get json object response but need to pass
     * json object as a request param.
     *
     * @param url                  server url
     * @param rp                   json string request
     * @param isToken              is token
     * @param onWebServiceListener listner
     */
    public void putJsonToJsonObject(String url, String rp, final boolean isToken, final OnWebServiceListener onWebServiceListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, rp, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response.toString());
                onWebServiceListener.successResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error : " + error.toString());
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.SESSION_NAME) + "=" + preferencesHelper.getPrefString(PreferencesHelper.SESSION_ID));
                if (isToken) {
                    headers.put("X-CSRF-Token", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                    Log.i("TOKEN::", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                }
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }

    /**
     * This function is used to call json post request and get json array response but need to pass
     * key value object as a request param.
     *
     * @param url                  server url
     * @param rp                   json string request
     * @param isToken              is token
     * @param onWebServiceListener listner
     */
    public void postKeyValueToJsonArray(String url, Map<String, String> rp, final boolean isToken, final OnArrayWebServiceListener onWebServiceListener) {
        Log.d(TAG, url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(rp), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response);
                onWebServiceListener.successResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error : " + error.toString());
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.SESSION_NAME) + "=" + preferencesHelper.getPrefString(PreferencesHelper.SESSION_ID));
                if (isToken) {
                    headers.put("X-CSRF-Token", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                    Log.i("TOKEN::", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                }
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }

    /**
     * This function is used when need to send get request to user and get json array request to the user.
     *
     * @param url                  pass server address
     * @param rp                   for get request pass rp null here
     * @param isToken              need to attached token if yes then pass true else false
     * @param onWebServiceListener listner class for webservice.
     */
    public void getKeyValueToJsonArray(String url, Map<String, String> rp, final boolean isToken, final OnArrayWebServiceListener onWebServiceListener) {
        Log.d(TAG, url);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response);
                onWebServiceListener.successResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error : " + error.toString());
                //Log.e("Response", "Error : " + new String(error.networkResponse.data)+error.networkResponse.statusCode+error.networkResponse.headers);
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.SESSION_NAME) + "=" + preferencesHelper.getPrefString(PreferencesHelper.SESSION_ID));
                if (isToken) {
                    headers.put("X-CSRF-Token", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                    // headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.session_name, "") + "=" + preferencesHelper.getString(PreferencesData.session_id, ""));
                    Log.i("TOKEN::", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
                }
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }

    /**
     * This function is used when need to send get request to user and get json object request to the user.
     *
     * @param url                  pass server address
     * @param rp                   for get request pass rp null here
     * @param isToken              need to attached token if yes then pass true else false
     * @param onWebServiceListener listner class for webservice.
     */
    public void getKeyValueToJsonObject(String url, Map<String, String> rp, final boolean isToken, final OnWebServiceListener onWebServiceListener) {
        // public void setUserLogin() {
        Log.d(TAG, url);
        CustomRequest request = new CustomRequest(context, Request.Method.GET, url, rp, isToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "RESPONSE :SUCCESS " + response.toString());
                onWebServiceListener.successResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //// Log.e("Response", "Error : " + new String(error.networkResponse.data)+error.networkResponse.statusCode+error.networkResponse.headers);
                handleErrorResponse(error, onWebServiceListener, isToken);
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(ApiConstants.TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
//        mRequestQueue.getCache().clear();
        mRequestQueue.add(request);

    }


    /**
     * This listner is used when user send json object request.
     */
    public interface OnWebServiceListener {

        //Webservice success response
        void successResponse(JSONObject content);

        //Webservice failure response
        void failureResponse(String errorMessage, int errorCode);
    }

    /**
     * This listner is used when user send json array request to server
     */
    public interface OnArrayWebServiceListener {

        //Webservice success response
        void successResponse(String content);

        //Webservice failure response
        void failureResponse(String errorMessage, int errorCode);
    }
}
