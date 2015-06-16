package com.musica.cortador.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.musica.cortador.R;
import com.musica.cortador.adapter.NavDrawerAdapter;
import com.musica.cortador.listner.ClickListner;
import com.musica.cortador.listner.RecyclerTouchListner;
import com.musica.cortador.model.NavDrawerItem;
import com.musica.cortador.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private static final String LOG="Example";

    private RecyclerView recyclerView;

    private static final String FILE_NAME="sharedPrefereances";

    private static final String KEY_USER_LEARN_DRAWER="KEY_USER_LEARN_DRAWER";

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private Boolean mUserLearnDrawer;

    private Boolean mSavedInstanceState=true;

    private View containerView;

    private NavDrawerAdapter mNavDrawerAdapter;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.drawerList);
        // Inflate the layout for this fragment

        mNavDrawerAdapter=new NavDrawerAdapter(getActivity(),getData());
        recyclerView.setAdapter(mNavDrawerAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListner(getActivity(),recyclerView,new ClickListner() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity(), "onClick is called"+position,Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawers();
                ((HomeActivity)getActivity()).onDrawerItemClicked(position);
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(),"onLongClick is called"+position,Toast.LENGTH_LONG).show();
//                mDrawerLayout.closeDrawers();
            }
        }));
        return layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnDrawer=Boolean.getBoolean(readToPreference(getActivity(),KEY_USER_LEARN_DRAWER,"false"));
        if(savedInstanceState!=null){
            mSavedInstanceState=true;
        }
    }

    public void setUp(int fragmentId,DrawerLayout drawerLayout, Toolbar toolBar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolBar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnDrawer){
                    mUserLearnDrawer=true;
                    saveToPreference(getActivity(),KEY_USER_LEARN_DRAWER,mUserLearnDrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
//                mDrawerLayout.closeDrawers();

            }


        };
        if(!mUserLearnDrawer && !mSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }



    public static List<NavDrawerItem> getData(){
        List<NavDrawerItem> data=new ArrayList<>();

        int[] icons={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};

        String[] titles={"HOME","FRIENDS","MESSAGE","ABOUT US","LOGOUT"};

        for(int i=0;i<icons.length && i<titles.length;i++){
            NavDrawerItem current=new NavDrawerItem(icons[i],titles[i]);

            data.add(current);
        }

        return data;
    }

    public static void saveToPreference(Context context,String key,String value){

        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();

    }

    public static String readToPreference(Context context,String key,String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND);
        return sharedPreferences.getString(key,value);
    }

}
