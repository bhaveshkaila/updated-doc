package com.golden11.app.volleyWebservice;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.golden11.app.localStorage.PreferencesHelper;

public class CustomRequest extends Request<JSONObject> {

    private final Listener<JSONObject> listener;
    private final Map<String, String> params;
    private boolean isToken = false;
    PreferencesHelper preferencesHelper;
    Context mContext;

    public CustomRequest(Context context, String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        mContext = context;
        preferencesHelper = new PreferencesHelper(mContext);
    }

    public CustomRequest(Context context, int method, String url, Map<String, String> params, boolean token, Listener<JSONObject> reponseListener,
                         ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        mContext = context;
        isToken = token;
        preferencesHelper = new PreferencesHelper(mContext);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.SESSION_NAME) + "=" + preferencesHelper.getPrefString(PreferencesHelper.SESSION_ID));
        if (isToken) {
            headers.put("X-CSRF-Token", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
            // headers.put("Cookie", preferencesHelper.getPrefString(PreferencesHelper.session_name, "") + "=" + preferencesHelper.getString(PreferencesData.session_id, ""));
            Log.i("TOKEN::", preferencesHelper.getPrefString(PreferencesHelper.TOKEN));
        }
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    };

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i("RESSSS::", "--" + jsonString);
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
