package com.musica.cortador.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;

import com.musica.cortador.R;
import com.musica.cortador.ui.fragment.FriendsFragment;
import com.musica.cortador.ui.fragment.HomeFragment;
import com.musica.cortador.ui.fragment.MessagesFragment;
import com.musica.cortador.ui.fragment.SecondFragment;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

public class HomeActivity extends BaseActivity {


    private static String TAG = HomeActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private Menu mOptionsMenu;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set custom Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setToolbar(this,R.layout.toolbar,mToolbar,false);

        addNavigationDrawer(R.id.drawer_layout,R.id.fragment_navigation_drawer,mToolbar);

        HomeFragment homeFragment=new HomeFragment();
        replaceFragment(R.id.container_home, homeFragment);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean optionMenu= super.onCreateOptionsMenu(menu);
        showSearchButton(true);
        showAddButton(true);
        showRefreshButton(true);
        showSettingButton(false);
        return optionMenu;
    }

    public void onDrawerItemClicked(int position){

        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                replaceFragment(R.id.container_home, homeFragment);
                break;
            case 1:
                MessagesFragment msgFragment=new MessagesFragment();
                replaceFragment(R.id.container_home, msgFragment);
                break;
            case 2:
                FriendsFragment frdFragment=new FriendsFragment();
                replaceFragment(R.id.container_home,frdFragment);
                break;
            case 3:
                SecondFragment secondFragment=new SecondFragment();
                replaceFragment(R.id.container_home,secondFragment);
                break;
            case 4:
                FriendsFragment fragment=new FriendsFragment();
                replaceFragment(R.id.container_home,fragment);
                break;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_search){
            startActivity(new Intent(this,TabLibActivity.class));
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
}
