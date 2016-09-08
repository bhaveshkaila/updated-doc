package com.golden11.app.volleyWebservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class RequestParam {


    //Child json object
    Map<String, String> objChild = new HashMap<String, String>();

    //HashMap for the Header Request call
    private Map<String, String> map = new HashMap<>();

    public RequestParam() {
        objChild = new HashMap<String, String>();
        map = new HashMap<>();
    }

    public void addChild(String key, String value) {
        try {
            objChild.put(key, value);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String getChildString() {
        return objChild.toString();
    }


    public Map<String, String> getChild() {
        return objChild;
    }

    public boolean removeChildObject(String paramName) {
        try {
            objChild.remove(paramName);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    public void putHeader(String key, String value) {
        map.put(key, value);
    }

    public Map<String, String> getHeaderRequestParam() {
        return map;
    }


}
