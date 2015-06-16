package com.musica.cortador.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.musica.cortador.R;
import com.musica.cortador.ui.fragment.NavigationDrawerFragment;


public class BaseActivity extends ActionBarActivity {

    private static String TAG = BaseActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private Menu mOptionsMenu;

//    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_full_appbar);

        //Set custom Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setToolbar(this,R.layout.toolbar,mToolbar,true);

//        addNavigationDrawer(R.id.drawer_layout,R.id.fragment_navigation_drawer,mToolbar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        mOptionsMenu=menu;
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
//            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            startActivity(new Intent(this,HomeActivity.class));
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


    public void setToolbar(ActionBarActivity context,int toolBarLayout,Toolbar toolBar,boolean isSubActivity) {

        context.setSupportActionBar(toolBar);
        context.getSupportActionBar().setHomeButtonEnabled(true);
        if (isSubActivity) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void addNavigationDrawer(int drawerLayoutId,int fragmentNavDrawerId,Toolbar toolbar){
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(drawerLayoutId);

        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(fragmentNavDrawerId);

        drawerFragment.setUp(fragmentNavDrawerId,drawerLayout,toolbar);
    }

    public void addFragment(int containerId, Fragment fragment,boolean addToBackStack) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(containerId, fragment, fragment.getClass().getName());
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getName());
            }else{
                transaction.addToBackStack(null);
            }
            transaction.commit();
    }

    public void replaceFragment(int containerId, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment, fragment.getClass().getName());
//        transaction.hide(fromFragment);
//        transaction.addToBackStack(toFragment.getClass().getName());
        transaction.commit();
    }

    public void showSearchButton(boolean show){
        if(mOptionsMenu!=null) {
            mOptionsMenu.findItem(R.id.action_search).setVisible(show);
        }

    }
    public void showRefreshButton(boolean show){
        if(mOptionsMenu!=null) {
            mOptionsMenu.findItem(R.id.action_refresh).setVisible(show);
        }

    }
    public void showAddButton(boolean show){
        if(mOptionsMenu!=null) {
            mOptionsMenu.findItem(R.id.action_add).setVisible(show);
        }

    }
    public void showSettingButton(boolean show){
        if(mOptionsMenu!=null) {
            mOptionsMenu.findItem(R.id.action_settings).setVisible(show);
        }

    }
}
