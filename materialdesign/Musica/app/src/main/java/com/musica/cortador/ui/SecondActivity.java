package com.musica.cortador.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.musica.cortador.R;
import com.musica.cortador.adapter.TabPagerAdapter;
import com.musica.cortador.tabs.SlidingTabLayout;
import com.musica.cortador.ui.fragment.FriendsFragment;
import com.musica.cortador.ui.fragment.FromMeFragment;
import com.musica.cortador.ui.fragment.MessagesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhavesh.Kaila on 18-May-15.
 */
public class SecondActivity extends BaseActivity {

    private  SlidingTabLayout mTabs;

    private ViewPager mViewPager;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setToolbar(this,R.layout.toolbar,mToolbar,true);

        mTabs=(SlidingTabLayout)findViewById(R.id.tabLayout);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);

        mViewPager.setAdapter(new TabPagerAdapter(this,getSupportFragmentManager(),getFragments()));

        mTabs.setCustomTabView(R.layout.custom_tab_layout, R.id.tabText);
        mTabs.setDistributeEvenly(true);
        mTabs.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.windowBackground));

        mTabs.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean optionMenu= super.onCreateOptionsMenu(menu);
        showSearchButton(true);
        showAddButton(false);
        showRefreshButton(false);
        showSettingButton(false);
        return optionMenu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SecondActivity.class));
            return true;
        }

        if(id == R.id.action_search){
            return true;
        }
        if(id == R.id.action_refresh){
            return true;
        }
        if(id == R.id.action_add){
            return true;
        }
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> getFragments(){
        List<Fragment> list=new ArrayList<>();

        FromMeFragment from=new FromMeFragment();
        FriendsFragment friends=new FriendsFragment();
        MessagesFragment messages=new MessagesFragment();

        list.add(from);
        list.add(messages);
        list.add(friends);

        return list;
    }

}
