package com.wink.activity.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.wink.R;
/**
 * Fragment Activity to be used as base for other activities.
 * @author moti.prajapati
 *
 */
public class BaseFragmentActivity extends FragmentActivity {

	
	public void addFragment(Fragment fragment){
		
		ViewGroup baseView = (ViewGroup)View.inflate(this, R.layout.base_activity_layout, null);
		setContentView(baseView);
		//baseView.findViewById(R.id.baseFragmentContainer);
		
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.baseLayout, fragment);
		fragmentTransaction.commit();
		
	}
}
