package com.example.xmlexample;

import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.xmlexample.model.Song;
import com.example.xmlexample.model.Songs;
import com.example.xmlexample.parse.MySingleton;
import com.example.xmlexample.parse.SimpleXmlRequest;
import com.example.xmlexample.parse.VolleySingleton;

public class MainActivity extends Activity {
	
	public static final String URL_GET_SONGS="http://www.singingportraits.com.au/getSongs.xml";
	private VolleySingleton mVolleySingleton;
	private RequestQueue mRequestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRequestQueue=mVolleySingleton.getRequestQueue();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getXmlData() {
		SimpleXmlRequest<Songs> xmlRequest = new SimpleXmlRequest<Songs>(Request.Method.GET, URL_GET_SONGS, Songs.class,  
			    new Response.Listener<Songs>() 
			    {
					@Override
			        public void onResponse(Songs response) {
			            
			        			            
			        
					 }
			    }, 
			    new Response.ErrorListener() 
			    {
			         @Override
			         public void onErrorResponse(VolleyError error) {                                
			            
			       }
			    }
			);  
		xmlRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		MySingleton.getInstance(getApplicationContext())
		.addToRequestQueue(xmlRequest);
		
	}
}
