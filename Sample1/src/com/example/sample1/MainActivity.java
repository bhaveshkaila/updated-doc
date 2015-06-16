package com.example.sample1;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.example.sample1.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements Callback<FeedlyResponse> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HashMap queryMap=new HashMap();
        queryMap.put("query","bhavesh");
        
		WebserviceManagmentManager.getInstance().getFeedlySuggestionsService().searchFeeds(queryMap, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void failure(RetrofitError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void success(FeedlyResponse arg0, Response arg1) {
		// TODO Auto-generated method stub
		
	}
}
