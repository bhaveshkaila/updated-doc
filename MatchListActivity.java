package com.fifa.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.fifa.android.adapter.MatchListAdapter;
import com.fifa.android.application.FifaApplication;
import com.fifa.android.customview.CustomProgressbar;
import com.fifa.android.listview.PullToRefresh_Master;
import com.fifa.android.listview.PullToRefresh_Master.OnRefreshListener;
import com.fifa.android.localstorage.UserSharedPreferences;
import com.wc.brasil.fifa.app.R;
import com.fifa.android.model.Matches;
import com.fifa.android.utils.AppConstant;
import com.fifa.android.utils.CommonUtils;
import com.fifa.android.utils.ValidationUtils;
import com.fifa.android.webservices.WebServiceManager;
import com.fifa.android.webservices.responsebean.CompetationMatchResponse;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
@SuppressLint({ "ShowToast", "ResourceAsColor" })
@EActivity
public class MatchListActivity  extends SherlockFragmentActivity implements OnItemClickListener  {

	private String competitionId;
	
	private String competitionName;
	
	private PullToRefresh_Master lvmatchList;
	
	private MatchListAdapter mAdapter;
	
	private List<Matches> mMatchList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matchlist_activity);
		
		lvmatchList=(PullToRefresh_Master)findViewById(R.id.lvmatchList);
		mMatchList=new ArrayList<Matches>();
		mAdapter=new MatchListAdapter(this, mMatchList);

		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header));
		getSupportActionBar().setTitle(competitionName);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitleColor(R.color.white);
		lvmatchList.setOnItemClickListener(this);
		
		(lvmatchList).setOnRefreshListener(new OnRefreshListener() 
	        {
	            @Override
	            public void onRefresh() 
	            {
	            	afterview();
	            }
	        });
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        SubMenu sub = menu.addSubMenu(0, 1, 0, "").setIcon(getResources().getDrawable(R.drawable.ic_action_refresh));
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	finish();
	        return true;
	    case 1:
	    	afterview();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	@AfterViews
	public void afterview(){
		
		Bundle bundle=getIntent().getExtras();
		
		competitionId=bundle.getString(AppConstant.MATCH_CENTER.COMPETITIONID);
		competitionName=bundle.getString(AppConstant.MATCH_CENTER.COMPETITION_EN);
		if(CommonUtils.isNetworkAvailable(this) && competitionId !=null){
			CustomProgressbar.showProgressBar(this, false);
			getCompetitionDetail();
		}else{
			Toast.makeText(this, getResources().getString(R.string.newwork_error), Toast.LENGTH_LONG).show();
		}
	}
	@Background
	public void getCompetitionDetail(){
		
		CompetationMatchResponse  response=WebServiceManager.getInstance(this).getCompetitionMatchDetail(competitionId);
		if(response!=null){
			afterGetCompetitionDetail(response);
		}else{
			CustomProgressbar.hideProgressBar();
			Toast.makeText(this, getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
			
		}
	}
	@UiThread
	public void afterGetCompetitionDetail(CompetationMatchResponse competationMatchResponse){
		
		CustomProgressbar.hideProgressBar();
		if (!ValidationUtils.isSuccessResponse(competationMatchResponse)) {
			Toast.makeText(this, getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
			return;
		}else
		{
			UserSharedPreferences.getInstance(this).putString(AppConstant.COMMON.DATE, competationMatchResponse.expires);
			mMatchList=competationMatchResponse.data.matches;
			mAdapter.setData(mMatchList);
			mAdapter.notifyDataSetChanged();
			lvmatchList.setAdapter(mAdapter);
		}
		(lvmatchList ).onRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		Matches match=mMatchList.get(position-1);
		if(match!=null ){
			Intent imatchdetail=new Intent(this, MatchDetailActivity_.class);
			imatchdetail.putExtra(AppConstant.MATCH_DETAIL.MATCHID, match.MatchID);
			imatchdetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(imatchdetail);
		}
	}
	
	@Override
	  public void onStart() {
	    super.onStart();

	    FifaApplication.TRACKER_MANAGER.ScreenStart(this); // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    FifaApplication.TRACKER_MANAGER.ScreenStop(this); // Add this method.
	  }

}
