package com.musica.cortador.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.musica.cortador.R;
import com.musica.cortador.adapter.TabPagerAdapter;
import com.musica.cortador.ui.fragment.FriendsFragment;
import com.musica.cortador.ui.fragment.FromMeFragment;
import com.musica.cortador.ui.fragment.MessagesFragment;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TabLibActivity extends BaseActivity implements MaterialTabListener {

    private Toolbar mToolbar;

    private MaterialTabHost mTabHost;

    private ViewPager mViewPager;
    private TabPagerAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_lib);

        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setToolbar(this,R.layout.toolbar,mToolbar,true);

        mTabHost=(MaterialTabHost)findViewById(R.id.materialTabHost);
        mViewPager=(ViewPager)findViewById(R.id.tabViewPager);

        mTabAdapter=new TabPagerAdapter(this,getSupportFragmentManager(),getFragments());
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mTabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i <mTabAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setText(mTabAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
            mTabHost.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_lib, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        mViewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

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
